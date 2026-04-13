package com.ershou.ershou.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sys_log")
public class LoginInfo extends Model implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "log_id", type = IdType.AUTO)
    private Integer logId;

    private String username;

    private String loginLocation;

    private String browser;

    private String os;

    private Integer status;

    private String msg;

    private Date loginTime;

    private Integer delFlag;
}
