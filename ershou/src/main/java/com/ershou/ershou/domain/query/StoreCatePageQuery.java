package com.ershou.ershou.domain.query;

import com.ershou.ershou.domain.Pagequery;
import lombok.Data;

@Data
public class StoreCatePageQuery extends Pagequery {
    private Integer cateId;

    private Integer isShow;

    private String cateName;
}
