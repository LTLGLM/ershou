package com.ershou.ershou.domain.query;

import com.ershou.ershou.domain.Pagequery;
import lombok.Data;

@Data
public class LoginInfoPageQuery extends Pagequery {
    private String loginLocation;

    private String username;

    private Integer status;

    private String starttime;

    private String endtime;
}
