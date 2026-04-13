package com.ershou.ershou.utils;

import com.ershou.ershou.domain.dto.MenuDto;
import com.ershou.ershou.domain.po.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TreeBuilder {
    public static List<MenuDto> buildMenuTree(List<MenuDto> menuList) {
        HashMap<Integer, MenuDto> menuMap = new HashMap<>();

        ArrayList<MenuDto> tree = new ArrayList<>();

        // 初始化Map
        for (MenuDto menuDto : menuList) {
            menuMap.put(menuDto.getMenuId(), menuDto);
        }

        // 构建 menuList
        for (MenuDto menuDto : menuList) {
            if (menuDto.getPid() == 0) {
                tree.add(menuDto);
            } else {
                MenuDto parent = menuMap.get(menuDto.getPid());
                if (parent != null) {
                    parent.getChildren().add(menuDto);
                }
            }
        }

        return tree;
    }
}
