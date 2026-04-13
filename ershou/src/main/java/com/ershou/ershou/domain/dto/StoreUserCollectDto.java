package com.ershou.ershou.domain.dto;

import com.ershou.ershou.domain.po.StoreUserCollect;
import lombok.Data;

@Data
public class StoreUserCollectDto extends StoreUserCollect {
    private String username;

    private String goodName;

    private String image;

    private String goodInfo;

    private Double price;
}
