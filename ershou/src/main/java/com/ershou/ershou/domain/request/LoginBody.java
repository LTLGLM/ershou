package com.ershou.ershou.domain.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="登录表单")
public class LoginBody implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private Integer code;
    private String uuid;
}
