package com.ershou.ershou.domain.query;

import com.ershou.ershou.domain.Pagequery;
import lombok.Data;

import java.util.List;

@Data
public class StoreGoodPageQuery extends Pagequery {

    private Integer firstCateId;

    private Integer secondCateId;

    private String username;

    private String goodName;

    private List<Integer> selectCateId;
}
