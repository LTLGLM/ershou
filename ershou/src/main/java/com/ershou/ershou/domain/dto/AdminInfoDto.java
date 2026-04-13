package com.ershou.ershou.domain.dto;

import com.ershou.ershou.domain.po.Admin;
import com.ershou.ershou.domain.po.Role;
import com.ershou.ershou.domain.Router;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AdminInfoDto extends Admin implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<String> btnList;

    private List<Role> roleList;

    private List<Router> routerList;
}
