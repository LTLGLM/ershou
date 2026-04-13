package com.ershou.ershou.domain.vo;

import com.ershou.ershou.domain.po.StoreUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class StoreUserVo extends StoreUser {
    @JsonIgnore
    @Override
    public String getPassword() {
        return super.getPassword();
    }
}
