package com.ershou.ershou.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value="sys_role_menu")
public class RoleMenu extends Model implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value="role_id",type= IdType.AUTO)
    private Integer roleId;

    private Integer menuId;
}
