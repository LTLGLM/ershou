package com.ershou.ershou.domain.dto;

import com.ershou.ershou.domain.po.Admin;
import com.ershou.ershou.domain.po.Role;
import lombok.Data;

import java.util.List;

@Data
public class AdminDto extends Admin {
    private List<Role> roles;
}
