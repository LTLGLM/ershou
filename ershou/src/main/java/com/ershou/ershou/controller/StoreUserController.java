package com.ershou.ershou.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.PageResult;
import com.ershou.ershou.domain.po.StoreUser;
import com.ershou.ershou.domain.query.StoreUserPageQuery;
import com.ershou.ershou.domain.vo.LoginUserVo;
import com.ershou.ershou.domain.vo.StoreUserVo;
import com.ershou.ershou.service.StoreUserService;
import com.ershou.ershou.utils.AjaxResult;
import com.ershou.ershou.utils.BeanCopyUtils;
import com.ershou.ershou.utils.SecurityAdminUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-11-06
 */
@RestController
@RequestMapping("/storeUser")
public class StoreUserController {

    @Autowired
    private StoreUserService storeUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // 获取小程序用户列表
    @PreAuthorize("hasAuthority('usermanager:vipuser:list')")
    @GetMapping("/list")
    public AjaxResult getStoreUserList(StoreUserPageQuery storeUserPageQuery) {
        LoginUserVo loginUserVo = SecurityAdminUtils.getAdmin();
        LambdaQueryWrapper<StoreUser> storeUserLambdaQueryWrapper = new LambdaQueryWrapper<StoreUser>().eq(StoreUser::getDeleted, 0);
        if (storeUserPageQuery.getUsername() != null && !storeUserPageQuery.getUsername().isEmpty()) {
            storeUserLambdaQueryWrapper.like(StoreUser::getUsername, storeUserPageQuery.getUsername());
        }
        if (storeUserPageQuery.getMobile() != null && !storeUserPageQuery.getMobile().isEmpty()) {
            storeUserLambdaQueryWrapper.like(StoreUser::getMobile, storeUserPageQuery.getMobile());
        }
        if (storeUserPageQuery.getStarttime() != null && !storeUserPageQuery.getStarttime().isEmpty()) {
            storeUserLambdaQueryWrapper.ge(StoreUser::getCreateTime, storeUserPageQuery.getStarttime());
        }
        if (storeUserPageQuery.getEndtime() != null && !storeUserPageQuery.getEndtime().isEmpty()) {
            storeUserLambdaQueryWrapper.le(StoreUser::getCreateTime, storeUserPageQuery.getEndtime());
        }
        Page<StoreUser> storeUserPage = new Page<>(storeUserPageQuery.getPageNum(), storeUserPageQuery.getPageSize());
        // 获取小程序用户列表
        Page<StoreUser> page = storeUserService.page(storeUserPage, storeUserLambdaQueryWrapper);

        // 新建StoreUserVo
        Page<StoreUserVo> storeUserVoPage = new Page<>();

        // 拷贝 Page<StoreUser>到Page<StoreUserVo>
        BeanUtils.copyProperties(page, storeUserVoPage);

        List<StoreUserVo> storeUserVoList = BeanCopyUtils.copyPropertiesList(page.getRecords(), StoreUserVo.class);

        storeUserVoPage.setRecords(storeUserVoList);

        PageResult<StoreUserVo> storeUserPageResult = new PageResult<>(storeUserVoPage);

        return AjaxResult.success(storeUserPageResult);
    }

    // 查询当个用户信息
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('usermanager:vipuser:edit')")
    public AjaxResult getStoreUserById(@PathVariable Long id) {
        StoreUser storeUser = storeUserService.getById(id);
        StoreUserVo storeUserVo = BeanCopyUtils.copyProperties(storeUser, StoreUserVo.class);
        return AjaxResult.success(storeUserVo);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('usermanager:vipuser:add')")
    public AjaxResult addStoreUser(@RequestBody StoreUser storeUser) {
        LambdaQueryWrapper<StoreUser> storeUserLambdaQueryWrapper = new LambdaQueryWrapper<StoreUser>().eq(StoreUser::getDeleted, 0).eq(StoreUser::getUsername, storeUser.getUsername());
        Map<String, Object> map = storeUserService.getMap(storeUserLambdaQueryWrapper);
        if (map != null) {
            return AjaxResult.error("该用户名已存在");
        }
        storeUser.setCreateTime(new Date());
        storeUser.setPassword(passwordEncoder.encode(storeUser.getPassword()));
        storeUser.setStatus(0);
        // 新增小程序用户
        boolean addStoreUserResult = storeUserService.save(storeUser);
        if (!addStoreUserResult) {
            return AjaxResult.error("添加失败");
        }
        return AjaxResult.success("添加成功");
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('usermanager:vipuser:edit')")
    public AjaxResult updateStoreUserById(@RequestBody StoreUser storeUser) {
        storeUser.setUpdateTime(new Date());
        // 更新小程序用户
        boolean updateStoreUserResult = storeUserService.updateById(storeUser);
        if (!updateStoreUserResult) {
            return AjaxResult.error("更新失败");
        }
        return AjaxResult.success("更新成功");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('usermanager:vipuser:delete')")
    public AjaxResult deleteStoreUserById(@PathVariable Long id) {
        // 逻辑删除小程序用户
        LambdaUpdateWrapper<StoreUser> storeUserLambdaUpdateWrapper = new LambdaUpdateWrapper<StoreUser>()
                .eq(StoreUser::getUserId, id)
                .set(StoreUser::getUpdateTime, new Date())
                .set(StoreUser::getDeleted, 1);
        boolean deleteStoreUserResult = storeUserService.update(storeUserLambdaUpdateWrapper);
        if (!deleteStoreUserResult) {
            return AjaxResult.error("删除失败");
        }
        return AjaxResult.success("删除成功");
    }

    // 批量删除小程序用户
    @DeleteMapping()
    @PreAuthorize("hasAuthority('usermanager:vipuser:deletes')")
    public AjaxResult deleteStoreUsers(@RequestBody List<Integer> ids) {
        // 逻辑删除小程序用户
        LambdaUpdateWrapper<StoreUser> storeUserLambdaUpdateWrapper = new LambdaUpdateWrapper<StoreUser>()
                .in(StoreUser::getUserId, ids)
                .set(StoreUser::getUpdateTime, new Date())
                .set(StoreUser::getDeleted, 1);
        boolean deleteStoreUserResult = storeUserService.update(storeUserLambdaUpdateWrapper);
        if (!deleteStoreUserResult) return AjaxResult.error("批量删除记录失败");
        return AjaxResult.success("批量删除记录成功");
    }
}
