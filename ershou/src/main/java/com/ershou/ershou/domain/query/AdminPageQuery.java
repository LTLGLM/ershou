package com.ershou.ershou.domain.query;

import com.ershou.ershou.domain.Pagequery;
import lombok.Data;

import java.util.Date;

@Data
public class AdminPageQuery extends Pagequery {
    private String username;

    private String roles;

    private String starttime;

    private String endtime;
}
