package com.ershou.ershou.domain.query;

import com.ershou.ershou.domain.Pagequery;
import lombok.Data;

@Data
public class RolePageQuery extends Pagequery {
    private String roleName;

    private String roleKey;

    private Integer roleStatus;

    private String starttime;

    private String endtime;
}
