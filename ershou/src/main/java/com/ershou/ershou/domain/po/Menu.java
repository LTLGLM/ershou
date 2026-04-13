package com.ershou.ershou.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sys_menu")
public class Menu extends Model implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId("menu_id")
    private Integer menuId;

    private Integer pid;

    private String menuName;

    private Integer menuOrder;

    private String path;

    private String component;

    private String menuType;

    private String visible;

    private Integer menuStatus;

    private String perms;

    private String icon;

    private Integer createBy;

    private Date createTime;

    private Integer updateBy;

    private Date updateTime;

    private Integer delFlag;

    private String remark;

}
