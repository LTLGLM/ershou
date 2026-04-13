package com.ershou.ershou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ershou.ershou.domain.po.Role;
import com.ershou.ershou.mapper.RoleMapper;
import com.ershou.ershou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private  RoleMapper roleMapper;

    @Override
    public List<Role> getRoles(Integer adminId) {
        return roleMapper.getRoles(adminId);
    }

}
