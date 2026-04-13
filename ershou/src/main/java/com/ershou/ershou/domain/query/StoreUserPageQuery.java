package com.ershou.ershou.domain.query;

import com.ershou.ershou.domain.Pagequery;
import lombok.Data;

@Data
public class StoreUserPageQuery extends Pagequery {
    private String username;

    private String mobile;

    private String starttime;

    private String endtime;
}
