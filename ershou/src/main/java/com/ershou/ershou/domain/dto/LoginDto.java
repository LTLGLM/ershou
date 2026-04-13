package com.ershou.ershou.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "LoginResponse对象",description = "PC登录返回对象")
public class LoginDto implements Serializable {

    @ApiModelProperty(value="adminId")
    private Integer adminId;

    @ApiModelProperty(value="用户账号")
    private String username;

    @ApiModelProperty(value="token")
    private String token;
}
