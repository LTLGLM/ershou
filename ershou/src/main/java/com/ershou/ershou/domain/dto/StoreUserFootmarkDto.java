package com.ershou.ershou.domain.dto;

import com.ershou.ershou.domain.po.StoreUserFootmark;
import lombok.Data;

@Data
public class StoreUserFootmarkDto extends StoreUserFootmark {
    private String username;

    private String goodName;

    private String image;

    private String goodInfo;

    private Double price;
}
