package com.ershou.ershou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.PageResult;
import com.ershou.ershou.domain.dto.AdminDto;
import com.ershou.ershou.domain.po.Admin;
import com.ershou.ershou.domain.po.AdminRole;
import com.ershou.ershou.domain.po.Role;
import com.ershou.ershou.domain.query.AdminPageQuery;
import com.ershou.ershou.domain.vo.AdminVo;
import com.ershou.ershou.exception.GeneralBusinessException;
import com.ershou.ershou.service.AdminRoleService;
import com.ershou.ershou.service.AdminService;
import com.ershou.ershou.service.RoleService;
import com.ershou.ershou.utils.AjaxResult;
import com.ershou.ershou.utils.BeanCopyUtils;
import com.ershou.ershou.utils.FieldFiller;
import com.ershou.ershou.utils.SecurityAdminUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/adminUser")
@Api(tags = "管理端用户服务")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRoleService adminRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 获取用户列表
    @GetMapping("/list")
    @ApiOperation("获取用户列表")
    @PreAuthorize("hasAuthority('systemmanager:user:list')")
    public AjaxResult getAdminList(AdminPageQuery adminPageQuery) {
        // 查询出所有admin数据
        Page<AdminDto> adminList = adminService.getAdminList(adminPageQuery);
        // 进行adminDto到AdminVo的转换
        Page<AdminVo> adminVoPage = new Page<>();
        // 1.拷贝Page<AdminDto>数据到Page<AdminVo>
        BeanUtils.copyProperties(adminList, adminVoPage);
        // 2.对分页数据类型进行转换
        List<AdminVo> adminVos = BeanCopyUtils.copyPropertiesList(adminList.getRecords(), AdminVo.class);
        // 3.覆盖adminVoPage的records
        adminVoPage.setRecords(adminVos);
        PageResult<AdminVo> adminVoPageResult = new PageResult<>(adminVoPage);
        return AjaxResult.success(adminVoPageResult);
    }

    // 新增用户
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAuthority('systemmanager:user:add')")
    @PostMapping()
    @ApiOperation("新增用户")
    public AjaxResult addAdmin(@RequestBody AdminDto adminDto) {
        // 判断新增用户信息
        if (adminDto == null) {
            return AjaxResult.error("新增用户信息为空");
        }

        if (adminDto.getUsername() == null || adminDto.getUsername().isEmpty()) {
            return AjaxResult.error("用户名为空");
        }

        if (adminDto.getPassword() == null || adminDto.getPassword().isEmpty()) {
            return AjaxResult.error("密码为空");
        }

        if (adminDto.getRoles() == null || adminDto.getRoles().isEmpty()) {
            return AjaxResult.error("请为用户分配角色");
        }

        // 设置创建的用户ID和创建时间
        FieldFiller.addFillFields(adminDto);

        // 密码加密
        adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));

        // 添加用户
        // 判断是否存在该用户
        LambdaQueryWrapper<Admin> isAdmineq = new LambdaQueryWrapper<Admin>().eq(Admin::getUsername, adminDto.getUsername().trim()).eq(Admin::getDeleted, "0");
        Admin isAdmin = adminService.getOne(isAdmineq);
        if (isAdmin != null) {
            return AjaxResult.error("用户已存在");
        }
        // 不存在执行
        boolean addAdminResult = adminService.save(adminDto);
        Integer backadminId = adminDto.getAdminId();

        // 添加失败
        if (!addAdminResult) {
            throw new GeneralBusinessException("新增用户失败");
        }

        // 查找到该用户的角色
        LambdaQueryWrapper<AdminRole> lambdaQueryWrapper = new LambdaQueryWrapper<AdminRole>().eq(AdminRole::getAdminId, adminDto.getAdminId());
        // 1、先进行删除
        adminRoleService.remove(lambdaQueryWrapper);
        // 2、插入新的角色数据
        List<AdminRole> adminRoleList = adminDto.getRoles().stream().map(role -> {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(backadminId);
            adminRole.setRoleId(role.getRoleId());
            return adminRole;
        }).collect(Collectors.toList());
        boolean addAdminRoleResult = adminRoleService.saveBatch(adminRoleList);
        if (!addAdminRoleResult) {
            throw new GeneralBusinessException("为用户分配角色失败");
        }

        return AjaxResult.success("用户新增成功");
    }

    // 更新用户
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAuthority('systemmanager:user:edit')")
    @PutMapping()
    @ApiOperation("更新用户")
    public AjaxResult updateAdmin(@RequestBody AdminDto adminDto) {
        // 设置更新用户和更新时间
        FieldFiller.updateFillFields(adminDto);

        // 更新用户信息
        LambdaUpdateWrapper<Admin> eq = new LambdaUpdateWrapper<Admin>()
                .eq(Admin::getAdminId, adminDto.getAdminId())
                .set(Admin::getUsername, adminDto.getUsername())
                .set(Admin::getAvatar, adminDto.getAvatar());

        // 判断是否有密码字段
        if (adminDto.getPassword() != null) {
            // 如果有密码加密
            eq.set(Admin::getPassword, passwordEncoder.encode(adminDto.getPassword()));
        }

        boolean updateAdminResult = adminService.update(eq);

        if (!updateAdminResult) {
            throw new GeneralBusinessException("更新用户信息失败");
        }

        // 删除原来的角色
        LambdaQueryWrapper<AdminRole> lambdaQueryWrapper = new LambdaQueryWrapper<AdminRole>().eq(AdminRole::getAdminId, adminDto.getAdminId());
        adminRoleService.remove(lambdaQueryWrapper);

        // 重新插入新的角色
        List<AdminRole> adminRoleList = adminDto.getRoles().stream().map(role -> {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(adminDto.getAdminId());
            adminRole.setRoleId(role.getRoleId());
            return adminRole;
        }).collect(Collectors.toList());

        boolean updateAdminRoleResult = adminRoleService.saveBatch(adminRoleList);
        if (!updateAdminRoleResult) {
            throw new GeneralBusinessException("更新用户角色信息失败");
        }

        return AjaxResult.success("更新用户信息成功");
    }

    // 删除用户
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAuthority('systemmanager:user:delete')")
    @DeleteMapping("{id}")
    @ApiOperation("删除用户")
    public AjaxResult deleteAdminById(@PathVariable("id") Integer adminId) {
        Admin admin = adminService.getById(adminId);
        if (admin == null) return AjaxResult.error("该用户不存在");

        // 逻辑删除用户
        LambdaUpdateWrapper<Admin> eq = new LambdaUpdateWrapper<Admin>()
                .eq(Admin::getAdminId, adminId)
                .set(Admin::getDeleted, 1);
        boolean removeAdminResult = adminService.update(eq);
        // 删除用户绑定角色信息
        boolean removeAdminRoleResult = adminRoleService.removeById(adminId);

        if (!removeAdminResult || !removeAdminRoleResult) {
            return AjaxResult.error("删除用户失败");
        }

        return AjaxResult.success("用户已删除");
    }

    // 批量删除
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAuthority('systemmanager:user:deletes')")
    @DeleteMapping()
    @ApiOperation("批量删除用户")
    public AjaxResult deleteAdminIds(@RequestBody List<Integer> ids) {

        LambdaUpdateWrapper<Admin> adminLambdaUpdateWrapper = new LambdaUpdateWrapper<Admin>().in(Admin::getAdminId, ids).set(Admin::getDeleted, 1);

        boolean removeAdminResult;
        try {
            removeAdminResult = adminService.update(adminLambdaUpdateWrapper);
        } catch (Exception e) {
            throw new GeneralBusinessException("批量删除用户失败");
        }

        if (!removeAdminResult){
            return AjaxResult.error("批量删除用户失败");
        }

        return AjaxResult.success("批量删除用户成功");
    }

    // 根据ID获取用户信息
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('systemmanager:user:edit')")
    @ApiOperation("根据ID获取用户信息")
    @ApiImplicitParam(name = "Token", required = true, dataType = "String", paramType = "header")
    public AjaxResult getAdminById(@PathVariable("id") Long adminId) {
        Admin admin = adminService.getById(adminId);
        Integer getadminId = admin.getAdminId();
        List<Role> roles = roleService.getRoles(getadminId);
        AdminVo adminVo = BeanCopyUtils.copyProperties(admin, AdminVo.class);
        adminVo.setRoles(roles);
        return AjaxResult.success(adminVo);
    }


    // 查询用户名满足条件的用户
    @PreAuthorize("hasAuthority('systemmanager:user:list')")
    @GetMapping("/admin")
    @ApiOperation("查询包含某关键字用户")
    public AjaxResult getAdminByAdmin(@RequestParam("query") String query){
        query = query.trim();
        LambdaQueryWrapper<Admin> adminLambdaQueryWrapper = new LambdaQueryWrapper<Admin>().eq(Admin::getDeleted, 0)
                .like(Admin::getUsername, query);
        List<Admin> adminList = adminService.list(adminLambdaQueryWrapper);
        return AjaxResult.success(adminList);
    }
}

