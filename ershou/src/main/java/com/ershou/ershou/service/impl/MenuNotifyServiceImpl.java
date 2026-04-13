package com.ershou.ershou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ershou.ershou.domain.po.Admin;
import com.ershou.ershou.domain.po.AdminRole;
import com.ershou.ershou.domain.po.RoleMenu;
import com.ershou.ershou.domain.vo.LoginUserVo;
import com.ershou.ershou.service.AdminRoleService;
import com.ershou.ershou.service.AdminService;
import com.ershou.ershou.service.MenuNotifyService;
import com.ershou.ershou.service.RoleMenuService;
import com.ershou.ershou.utils.SecurityAdminUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuNotifyServiceImpl implements MenuNotifyService {

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private AdminRoleService adminRoleService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void roleNotifyUser(Integer roleId) {
        LoginUserVo loginUserVo = SecurityAdminUtils.getAdmin();
        Integer adminId = loginUserVo.getAdmin().getAdminId();
        Admin admin = adminService.getById(adminId);
        String username = admin.getUsername();

        HashMap<String, Object> menuNoticeMessage = new HashMap<>();
        menuNoticeMessage.put("type","menuNoticeMessage");
        menuNoticeMessage.put("status",200);
        menuNoticeMessage.put("message", "由于用户：" + username + " 进行了菜单操作，影响到您这边的角色，建议您刷新一下页面");

        List<Integer> roleList = new ArrayList<>();
        roleList.add(roleId);
        List<Integer> admins = getAdminsByRoleIds(roleList);

        if(admins != null && !admins.isEmpty()){
            menuNoticeMessage.put("adminIds", admins);
            rabbitTemplate.convertAndSend("notice_exchange", "menu.notice.key", menuNoticeMessage);
        }
    }

    @Override
    public void menuNotifyUser(Object menus) {
        LoginUserVo loginUserVo = SecurityAdminUtils.getAdmin();
        Integer adminId = loginUserVo.getAdmin().getAdminId();
        Admin admin = adminService.getById(adminId);
        String username = admin.getUsername();

        HashMap<String, Object> menuNoticeMessage = new HashMap<>();
        menuNoticeMessage.put("message", "由于用户：" + username + " 进行了菜单操作，影响到您这边的角色，建议您刷新一下页面");

        List<Integer> affectedAdminIds = getAffectedAdminIds(menus);

        if (affectedAdminIds != null && !affectedAdminIds.isEmpty()) {
            menuNoticeMessage.put("adminIds", affectedAdminIds);
            rabbitTemplate.convertAndSend("notice_exchange", "menu.notice.key", menuNoticeMessage);
        }
    }

    private List<Integer> getAffectedAdminIds(Object menus) {
        List<Integer> menuIds = new ArrayList<>();

        if (menus instanceof Integer) {
            menuIds.add((Integer) menus);
        } else if (menus instanceof List) {
            menuIds.addAll((List<Integer>) menus);
        }

        if (menuIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<Integer> roles = getRolesByMenuIds(menuIds);
        if (roles.isEmpty()) {
            return Collections.emptyList();
        }
        return getAdminsByRoleIds(roles);
    }


    private List<Integer> getRolesByMenuIds(List<Integer> menuIds) {
        return roleMenuService.list(new LambdaQueryWrapper<RoleMenu>()
                        .in(RoleMenu::getMenuId, menuIds)
                        .select(RoleMenu::getRoleId))
                .stream()
                .map(RoleMenu::getRoleId)
                .collect(Collectors.toList());
    }

    private List<Integer> getAdminsByRoleIds(List<Integer> roleIds) {
        return adminRoleService.list(new LambdaQueryWrapper<AdminRole>()
                        .in(AdminRole::getRoleId, roleIds)
                        .select(AdminRole::getAdminId))
                .stream()
                .map(AdminRole::getAdminId)
                .collect(Collectors.toList());
    }

}
