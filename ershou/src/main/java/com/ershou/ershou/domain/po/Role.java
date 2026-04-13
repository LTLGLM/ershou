package com.ershou.ershou.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sys_role")
public class Role extends Model implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "role_id")
    private Integer roleId;

    private String roleName;

    private String roleKey;

    private Integer roleStatus;

    private Integer delFlag;

    private Integer createBy;

    private Date createTime;

    private Integer updateBy;

    private Date updateTime;

    private String remark;
}
