package com.ershou.ershou.domain.dto;

import com.ershou.ershou.domain.po.StoreCate;
import lombok.Data;

import java.util.List;

@Data
public class StoreCateDto extends StoreCate {
    private List<StoreCateSizeDto> storeCateSizeDtoList;
}
