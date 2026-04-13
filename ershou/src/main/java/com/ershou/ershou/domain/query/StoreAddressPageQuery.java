package com.ershou.ershou.domain.query;

import com.ershou.ershou.domain.Pagequery;
import lombok.Data;

@Data
public class StoreAddressPageQuery extends Pagequery{
    private String name;

    private String tel;

    private String username;

    private String floorName;

    private String starttime;

    private String endtime;
}
