package com.ershou.ershou.domain.dto;

import com.ershou.ershou.domain.po.Notice;
import lombok.Data;

@Data
public class NoticeDto extends Notice {
    private String username;
}
