package com.ershou.ershou.domain.dto;

import com.ershou.ershou.domain.po.StoreGood;
import lombok.Data;

@Data
public class StoreCartGoodDto extends StoreGood {
    private Integer addNum;
    private Integer checked;
    private Long cartInfoId;

}
