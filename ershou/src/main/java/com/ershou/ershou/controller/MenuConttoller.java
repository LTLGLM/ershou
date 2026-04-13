package com.ershou.ershou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ershou.ershou.domain.dto.MenuDto;

import com.ershou.ershou.domain.po.Menu;

import com.ershou.ershou.domain.po.RoleMenu;
import com.ershou.ershou.service.MenuNotifyService;
import com.ershou.ershou.service.MenuService;

import com.ershou.ershou.service.RoleMenuService;
import com.ershou.ershou.utils.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")
public class MenuConttoller {
    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuNotifyService menuNotifyService;

    @Autowired
    private RoleMenuService roleMenuService;


    // 获取所有菜单
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('systemmanager:menu:list')")
    public AjaxResult getMenuList(Menu menu) {

        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<Menu>().eq(Menu::getDelFlag, 0);
        if (menu.getMenuName() != null && !menu.getMenuName().isEmpty()) {
            menuLambdaQueryWrapper.like(Menu::getMenuName, menu.getMenuName());
        }
        if (menu.getMenuStatus() != null) {
            menuLambdaQueryWrapper.eq(Menu::getMenuStatus, menu.getMenuStatus());
        }

        return AjaxResult.success(menuService.list(menuLambdaQueryWrapper));
    }

    @GetMapping("/tree")
    public AjaxResult getMenuTree() {
        List<MenuDto> menuList = menuService.list(new LambdaQueryWrapper<Menu>().eq(Menu::getDelFlag, 0)).stream().map(menu -> {
            MenuDto menuDto = BeanCopyUtils.copyProperties(menu, MenuDto.class);
            return menuDto;
        }).collect(Collectors.toList());

        List<MenuDto> menuDtos = TreeBuilder.buildMenuTree(menuList);

        return AjaxResult.success(menuDtos);
    }

    // 获取单个菜单
    @PreAuthorize("hasAuthority('systemmanager:menu:edit')")
    @GetMapping
    public AjaxResult getMenuById(Integer menuId) {
        Menu menu = menuService.getById(menuId);
        return AjaxResult.success(menu);
    }

    // 添加菜单
    @PreAuthorize("hasAuthority('systemmanager:menu:add')")
    @PostMapping
    public AjaxResult addMenu(@RequestBody Menu menu) {
        FieldFiller.addFillFields(menu);
        boolean addMenuResult = menuService.save(menu);
        if (!addMenuResult) {
            return AjaxResult.error("添加菜单失败");
        }
        return AjaxResult.success("添加菜单成功");
    }

    // 更新菜单
    @PreAuthorize("hasAuthority('systemmanager:menu:edit')")
    @PutMapping
    public AjaxResult updateMenu(@RequestBody Menu menu) {
        FieldFiller.updateFillFields(menu);
        boolean updateMenuResult = menuService.updateById(menu);
        if (!updateMenuResult) {
            return AjaxResult.error("更新菜单失败");
        }
        return AjaxResult.success("更新菜单成功");
    }

    // 删除菜单
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasAuthority('systemmanager:menu:delete')")
    @DeleteMapping()
    public AjaxResult deleteMenu(@RequestBody List<Integer> menuIds) {
        LambdaUpdateWrapper<Menu> menuLambdaUpdateWrapper = new LambdaUpdateWrapper<Menu>().in(Menu::getMenuId, menuIds).set(Menu::getDelFlag, 1);
        boolean deleteMenuResult = menuService.update(menuLambdaUpdateWrapper);
        if (!deleteMenuResult) {
            return AjaxResult.error("删除菜单失败");
        }
        menuNotifyService.menuNotifyUser(menuIds);

        // 删除角色菜单表中拥有该菜单的数据
        boolean removeRoleMenu = roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().in(RoleMenu::getMenuId, menuIds));
        if(!removeRoleMenu){
            return AjaxResult.error("删除角色菜单失败");
        }

        return AjaxResult.success("删除菜单成功");
    }

}
