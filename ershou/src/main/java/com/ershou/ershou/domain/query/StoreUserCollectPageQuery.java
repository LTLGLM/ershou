package com.ershou.ershou.domain.query;

import com.ershou.ershou.domain.Pagequery;
import lombok.Data;

// 通用，用户收藏和浏览足迹
@Data
public class StoreUserCollectPageQuery extends Pagequery {
    private String username;

    private String goodName;

    private Double startprice;

    private Double endprice;

    private String starttime;

    private String endtime;
}
