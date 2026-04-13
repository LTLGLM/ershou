package com.ershou.ershou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ershou.ershou.domain.po.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<Role> getRoles(Integer adminId);
}
