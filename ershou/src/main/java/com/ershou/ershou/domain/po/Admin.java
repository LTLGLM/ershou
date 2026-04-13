package com.ershou.ershou.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sys_admin")
public class Admin extends Model implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "admin_id", type = IdType.AUTO)
    private Integer adminId;
    private String username;
    private String password;
    private String lastLoginIp;
    private Date lastLoginTime;
    private String avatar;
    private Date createTime;
    private Integer createBy;
    private Integer updateBy;
    private Date updateTime;
    private Integer deleted;
}
