package com.ershou.ershou.service;

public interface MenuNotifyService {

    void roleNotifyUser(Integer roleId);

    // 通过菜单更改查找通知用户
    void menuNotifyUser(Object menus);
}