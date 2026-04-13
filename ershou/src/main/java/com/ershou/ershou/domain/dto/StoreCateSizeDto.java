package com.ershou.ershou.domain.dto;

import com.ershou.ershou.domain.po.StoreCatesize;
import com.ershou.ershou.domain.po.StoreSizevalue;
import lombok.Data;

import java.util.List;

@Data
public class StoreCateSizeDto extends StoreCatesize {
    private List<StoreSizevalue> StoreSizeValueList;
}
