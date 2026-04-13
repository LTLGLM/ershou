package com.ershou.ershou.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ershou.ershou.domain.dto.MenuDto;
import com.ershou.ershou.domain.po.Menu;
import com.ershou.ershou.domain.po.RoleMenu;
import com.ershou.ershou.domain.vo.LoginUserVo;
import com.ershou.ershou.exception.GeneralBusinessException;
import com.ershou.ershou.service.MenuNotifyService;
import com.ershou.ershou.service.MenuService;
import com.ershou.ershou.service.RoleMenuService;
import com.ershou.ershou.utils.AjaxResult;
import com.ershou.ershou.utils.BeanCopyUtils;
import com.ershou.ershou.utils.SecurityAdminUtils;
import com.ershou.ershou.utils.TreeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2025-02-08
 */
@RestController
@RequestMapping("/roleMenu")
public class RoleMenuController {
    
    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuNotifyService menuNotifyService;
    
    @GetMapping("/{roleId}")
    public AjaxResult getRoleMenuByRoleId(@PathVariable("roleId") Integer roleId){
        LambdaQueryWrapper<RoleMenu> roleMenuLambdaQueryWrapper = new LambdaQueryWrapper<RoleMenu>()
                .eq(RoleMenu::getRoleId, roleId);
        List<RoleMenu> roleMenus = roleMenuService.list(roleMenuLambdaQueryWrapper);

        if(roleMenus.isEmpty()){
            return AjaxResult.success("该角色没有任何菜单权限");
        }

        List<Integer> roleMenuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());

        List<MenuDto> menuList = menuService.list(new LambdaQueryWrapper<Menu>().in(Menu::getMenuId, roleMenuIds).eq(Menu::getDelFlag,0)).stream().map(menu -> {
            MenuDto menuDto = BeanCopyUtils.copyProperties(menu, MenuDto.class);
            return menuDto;
        }).collect(Collectors.toList());


        List<MenuDto> treeMenuDtos = TreeBuilder.buildMenuTree(menuList);

        return AjaxResult.success("获取该角色菜单权限信息",treeMenuDtos);
    }

    @PutMapping("/{roleId}")
    public AjaxResult updateRoleMenuByRoleId(@PathVariable("roleId") Integer roleId, @RequestBody List<Integer> menuIds){
        LambdaQueryWrapper<RoleMenu> roleMenuLambdaQueryWrapper = new LambdaQueryWrapper<RoleMenu>()
               .eq(RoleMenu::getRoleId, roleId);
        roleMenuService.remove(roleMenuLambdaQueryWrapper);

        List<RoleMenu> roleMenus = menuIds.stream().map(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            return roleMenu;
        }).collect(Collectors.toList());

        try {
            roleMenuService.saveBatch(roleMenus);
        } catch (Exception e) {
            throw new GeneralBusinessException("更新角色菜单权限信息失败");
        }

        // 通过角色通知用户
        menuNotifyService.roleNotifyUser(roleId);

        return AjaxResult.success("更新角色菜单权限信息成功");
    }
}
