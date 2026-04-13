package com.ershou.ershou.domain.dto;

import com.ershou.ershou.domain.po.StoreAddress;
import lombok.Data;

@Data
public class StoreAddressDto extends StoreAddress {
    private String username;

    private String floorName;
}
