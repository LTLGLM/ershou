package com.ershou.ershou.domain.dto;


import com.ershou.ershou.domain.po.StoreCart;
import lombok.Data;

import java.util.List;

@Data
public class StoreCartDto extends StoreCart {
    private static final long serialVersionUID = 1L;

    private List<StoreCartGoodDto> storeCartGoodList;
}
