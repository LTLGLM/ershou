package com.ershou.ershou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.PageResult;
import com.ershou.ershou.domain.po.AdminRole;
import com.ershou.ershou.domain.po.Role;
import com.ershou.ershou.domain.query.RolePageQuery;
import com.ershou.ershou.exception.GeneralBusinessException;
import com.ershou.ershou.service.AdminRoleService;
import com.ershou.ershou.service.RoleService;
import com.ershou.ershou.utils.AjaxResult;
import com.ershou.ershou.utils.FieldFiller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private AdminRoleService adminRoleService;

    // 获取所有可选角色
    @GetMapping("/selectAll")
    public AjaxResult getSelectAllRoles() {
        // 获取可选的角色
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<Role>()
                .eq(Role::getDelFlag, 0).eq(Role::getRoleStatus, 0);
        List<Role> list = roleService.list(queryWrapper);
        return AjaxResult.success(list);
    }

    // 获取所有角色
    @PreAuthorize("hasAuthority('systemmanager:role:list')")
    @GetMapping("/list")
    public AjaxResult getRoleList(RolePageQuery rolePageQuery) {
        LambdaQueryWrapper<Role> roleQuery = new LambdaQueryWrapper<Role>()
                .eq(Role::getDelFlag, 0);
        if (!rolePageQuery.getRoleName().equals("") && rolePageQuery.getRoleName() != null) {
            roleQuery.like(Role::getRoleName, rolePageQuery.getRoleName());
        }
        if (!rolePageQuery.getRoleKey().equals("") && rolePageQuery.getRoleKey() != null) {
            roleQuery.like(Role::getRoleKey, rolePageQuery.getRoleKey());
        }
        if (rolePageQuery.getRoleStatus() != null) {
            roleQuery.eq(Role::getRoleStatus, rolePageQuery.getRoleStatus());
        }
        if (rolePageQuery.getStarttime() != null) {
            roleQuery.ge(Role::getCreateTime, rolePageQuery.getStarttime());
        }
        if (rolePageQuery.getEndtime() != null) {
            roleQuery.le(Role::getCreateTime, rolePageQuery.getEndtime());
        }
        // 构建分页器
        Page<Role> page = new Page<>(rolePageQuery.getPageNum(), rolePageQuery.getPageSize());
        Page<Role> rolePage = roleService.page(page, roleQuery);
        PageResult<Role> rolePageResult = new PageResult<>(rolePage);
        return AjaxResult.success(rolePageResult);
    }

    // 获取单个角色
    @PreAuthorize("hasAuthority('systemmanager:role:edit')")
    @GetMapping("/{id}")
    public AjaxResult getRoleById(@PathVariable("id") Integer roleId) {
        return AjaxResult.success(roleService.getById(roleId));
    }

    // 添加角色
    @PreAuthorize("hasAuthority('systemmanager:role:add')")
    @PostMapping()
    public AjaxResult addRoleById(@RequestBody Role role) {
        if (role == null) {
            return AjaxResult.error("新增角色信息为空");
        }
        if (role.getRoleName() == null || role.getRoleName().isEmpty()) {
            return AjaxResult.error("角色名不能为空");
        }
        if (role.getRoleKey() == null || role.getRoleKey().isEmpty()) {
            return AjaxResult.error("角色权限字符不能为空");
        }
        // 判断是否存在
        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<Role>()
                .eq(Role::getDelFlag, 0)
                .and(wrapper -> wrapper
                        .eq(Role::getRoleName, role.getRoleName())
                        .or()
                        .eq(Role::getRoleKey, role.getRoleKey()));
        Role isRole = roleService.getOne(roleLambdaQueryWrapper);
        if (isRole != null) {
            return AjaxResult.error("角色名称或标识已存在");
        }

        // 设置添加的用户和添加时间
        FieldFiller.addFillFields(role);

        // 添加角色
        boolean addRoleResult = roleService.save(role);
        // 添加失败
        if (!addRoleResult) {
            throw new GeneralBusinessException("添加角色失败");
        }

        return AjaxResult.success("添加角色成功");
    }

    // 更新角色信息
    @PreAuthorize("hasAuthority('systemmanager:role:edit')")
    @PutMapping()
    public AjaxResult updateRoleById(@RequestBody Role role) {
        // 设置更新用户和更新时间
        FieldFiller.updateFillFields(role);

        LambdaUpdateWrapper<Role> lambdaUpdateWrapper = new LambdaUpdateWrapper<Role>().eq(Role::getRoleId, role.getRoleId());
        boolean update = roleService.update(role, lambdaUpdateWrapper);

        if (!update) {
            return AjaxResult.error("更新角色信息失败");
        }
        return AjaxResult.success("更新角色信息成功");
    }

    // 删除角色
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAuthority('systemmanager:role:delete')")
    @DeleteMapping("/{id}")
    public AjaxResult deleteRoleById(@PathVariable("id") Integer roleId) {

        // 逻辑删除角色数据
        LambdaUpdateWrapper<Role> lambdaUpdateWrapper = new LambdaUpdateWrapper<Role>().eq(Role::getRoleId, roleId)
                .set(Role::getDelFlag, "1");
        boolean removeRoleResult = roleService.update(lambdaUpdateWrapper);

        // 删除AdminRole表中包含该角色的数据
        LambdaQueryWrapper<AdminRole> lambdaQueryWrapper = new LambdaQueryWrapper<AdminRole>().eq(AdminRole::getRoleId, roleId);
        boolean removeAdminRoleResult = adminRoleService.remove(lambdaQueryWrapper);

        if (!removeRoleResult) {
            throw new GeneralBusinessException("删除角色失败");
        }
        return AjaxResult.success("删除角色成功");
    }

    // 删除角色
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAuthority('systemmanager:role:deletes')")
    @DeleteMapping()
    public AjaxResult deleteRoleByIds(@RequestBody List<Integer> ids) {

        LambdaUpdateWrapper<Role> roleLambdaUpdateWrapper = new LambdaUpdateWrapper<Role>().in(Role::getRoleId, ids).set(Role::getDelFlag, 1);
        boolean removeRolesResult;
        try {
            removeRolesResult = roleService.update(roleLambdaUpdateWrapper);
        } catch (Exception e) {
            throw new GeneralBusinessException("批量删除角色失败");
        }

        if (!removeRolesResult){
            return AjaxResult.error("批量删除角色失败");
        }

        return AjaxResult.success("批量删除角色成功");
    }
}
