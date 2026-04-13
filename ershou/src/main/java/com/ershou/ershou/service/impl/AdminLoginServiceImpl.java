package com.ershou.ershou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ershou.ershou.constant.Constants;
import com.ershou.ershou.domain.po.Admin;
import com.ershou.ershou.domain.po.Menu;
import com.ershou.ershou.domain.po.Role;
import com.ershou.ershou.domain.Router;
import com.ershou.ershou.domain.request.LoginBody;
import com.ershou.ershou.domain.dto.AdminInfoDto;
import com.ershou.ershou.domain.dto.LoginDto;
import com.ershou.ershou.domain.vo.LoginUserVo;
import com.ershou.ershou.exception.GeneralBusinessException;
import com.ershou.ershou.mapper.AdminMapper;
import com.ershou.ershou.mapper.MenuMapper;
import com.ershou.ershou.mapper.RoleMapper;
import com.ershou.ershou.service.AdminLoginService;
import com.ershou.ershou.service.LoginInfoService;
import com.ershou.ershou.service.ValidateCodeService;
import com.ershou.ershou.utils.SecurityAdminUtils;
import com.ershou.ershou.utils.JwtUtils;
import com.ershou.ershou.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    private ValidateCodeService validateCodeService;

    @Autowired
    private LoginInfoService loginInfoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public LoginDto login(LoginBody loginBody, HttpServletRequest request) {
        // 判断验证码
        Boolean check = validateCodeService.check(loginBody.getUuid(), loginBody.getCode());
        if (!check) {
            loginInfoService.insertLoginInfo(loginBody, request, 1, "验证码不正确");
            throw new GeneralBusinessException("验证码不正确");
        }
        ;
        // 用户验证
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginBody.getUsername(), loginBody.getPassword()));
        } catch (AuthenticationException e) {
            if (e instanceof BadCredentialsException) {
                loginInfoService.insertLoginInfo(loginBody, request, 1, "密码错误");
                throw new GeneralBusinessException("用户不存在或密码错误");
            }
            throw new GeneralBusinessException(e.getMessage());
        }

        if (authentication == null) {
            loginInfoService.insertLoginInfo(loginBody, request, 1, "登录失败");
            throw new GeneralBusinessException("登录失败");
        }

        LoginUserVo loginUserVo = (LoginUserVo) authentication.getPrincipal();

        if (loginUserVo.getPermissions().isEmpty()) {
            throw new GeneralBusinessException("该用户的角色权限都没有分配，请联系管理远进行分配");
        }
        Admin admin = loginUserVo.getAdmin();

        admin.setLastLoginTime(new Date());
        // LambdaUpdateWrapper<Admin> lambdaUpdateWrapper = new LambdaUpdateWrapper<Admin>().eq(Admin::getAdminId, admin.getAdminId())
        //         .set(Admin::getLastLoginTime,new Date());
        adminMapper.updateById(admin);

        LoginDto loginDto = new LoginDto();

        String token = jwtUtils.createToken(loginUserVo);

        loginDto.setToken(token);
        BeanUtils.copyProperties(admin, loginDto);

        loginInfoService.insertLoginInfo(loginBody, request, 0, "登录成功");

        return loginDto;

    }

    @Override
    public void logout(HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserVo loginUserVo = (LoginUserVo) authentication.getPrincipal();
        String redisKey = Constants.LOGIN_TOKEN_KEY + loginUserVo.getToken();
        redisCache.deleteObject(redisKey);
        loginInfoService.insertLoginInfo(null, request, 0, "退出登录");

    }

    @Override
    public AdminInfoDto getAdminInfo() {

        LoginUserVo loginUserVo = SecurityAdminUtils.getAdmin();

        if (loginUserVo.getPermissions().isEmpty()) {
            throw new GeneralBusinessException("该用户的角色权限都没有分配，请联系管理远进行分配后在登录");
        }

        // 每次获取刷新权限
        List<Menu> perms = menuMapper.selectPermsByUserId(loginUserVo.getAdmin().getAdminId());
        loginUserVo.setPermissions(perms);
        // 清空 authorities，以便重新计算
        loginUserVo.setAuthorities(null);

        // 刷新权限，重新计算 authorities
        loginUserVo.getAuthorities();

        SecurityAdminUtils.setOrUpdateAdmin(loginUserVo);
        // 刷新redis用户信息
        jwtUtils.refreshToken(loginUserVo);

        AdminInfoDto adminInfoDto = new AdminInfoDto();
        BeanUtils.copyProperties(loginUserVo.getAdmin(), adminInfoDto);
        // 获取按钮权限
        List<String> btnList = loginUserVo.getPermissions().stream().map(Menu::getPerms).filter(fil -> !fil.endsWith("view")).collect(Collectors.toList());
        adminInfoDto.setBtnList(btnList);

        // 获取角色信息
        Integer adminId = loginUserVo.getAdmin().getAdminId();
        List<Role> roleList = roleMapper.getRoles(adminId);
        adminInfoDto.setRoleList(roleList);

        // 获取页面路由
        List<Menu> viewMenuList = loginUserVo.getPermissions().stream().filter(fil -> fil.getPerms().endsWith("view")).collect(Collectors.toList());
        // 获取父目录 menuId
        List<Integer> pidList = viewMenuList.stream().map(Menu::getPid).distinct().collect(Collectors.toList());
        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.in("menu_id", pidList);
        // 父目录
        List<Menu> pMenu = menuMapper.selectList(menuQueryWrapper);

        // 创建一个 List 用于存储 Menu 对象
        List<Menu> menuList = new ArrayList<>();

        // 添加权限和其他 Menu 对象到 List
        menuList.addAll(viewMenuList);
        menuList.addAll(pMenu);

        // 根据 menuId 进行排序
        menuList.sort(Comparator.comparing(Menu::getMenuId));

        // 创建路由列表
        ArrayList<Router> routers = new ArrayList<>();

        Map<String, Router> objHashMap = new HashMap<>();

        // 生成路由
        menuList.forEach(item -> {
            // 封装创建主目录路由方法
            Router router = createRouterFromMenu(item);
            // 如果是目录
            if (item.getPid() == 0 && "M".equals(item.getMenuType())) {
                // 处理路由名称
                String routerName = item.getComponent().substring(1);
                router.setName(routerName); // 去掉开头的 '/'
                ArrayList<Router> childrouters = new ArrayList<>();
                router.setChildren(childrouters);
                // 添加到map集合
                objHashMap.put(routerName, router);
            } else {
                // 如果是菜单
                String[] patharr = item.getComponent().split("/");
                router.setName(patharr[patharr.length - 1]); // 去掉开头的 '/'
                // 生成路由加载
                router.setComponent(item.getComponent());
                // 否则，寻找主菜单并添加为子路由\
                objHashMap.get(patharr[1]).getChildren().add(router);
            }
        });

        objHashMap.keySet().forEach(key -> routers.add(objHashMap.get(key)));

        adminInfoDto.setRouterList(routers);

        return adminInfoDto;
    }

    private static Router createRouterFromMenu(Menu item) {
        // 创建Router对象
        Router router = new Router();
        // 设置路由路劲
        router.setPath(item.getComponent());
        // 设置meta,meta中包含图标icon和title
        Map<String, String> meta = new HashMap<>();
        meta.put("title", item.getMenuName());
        meta.put("icon", item.getIcon());
        router.setMeta(meta);

        return router;
    }
}
