package com.ershou.ershou.domain.vo;

import com.ershou.ershou.domain.dto.AdminDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AdminVo extends AdminDto {
    @JsonIgnore
    @Override
    public String getPassword() {
        return super.getPassword();
    }
}
