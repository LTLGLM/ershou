package com.ershou.ershou.domain.dto;

import com.ershou.ershou.domain.po.StoreGood;
import lombok.Data;

@Data
public class StoreGoodDto extends StoreGood {
    private String username;

    private String cateName;
}
