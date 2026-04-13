package com.ershou.ershou.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_admin_role")
public class AdminRole extends Model implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "admin_id",type = IdType.INPUT)
    private Integer adminId;
    private Integer roleId;
}
