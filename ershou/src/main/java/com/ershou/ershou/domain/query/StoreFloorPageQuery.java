package com.ershou.ershou.domain.query;

import com.ershou.ershou.domain.Pagequery;
import lombok.Data;

@Data
public class StoreFloorPageQuery extends Pagequery {
    private String floorName;

    private String starttime;

    private String endtime;
}
