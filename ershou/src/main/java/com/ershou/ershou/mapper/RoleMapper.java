package com.ershou.ershou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ershou.ershou.domain.po.Role;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    List<Role> getRoles(Integer adminId);
}
