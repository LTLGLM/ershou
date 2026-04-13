package com.ershou.ershou.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author author
 * @since 2024-11-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_user")
public class StoreUser extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户名称
     */
    @TableField("username")
    private String username;

    /**
     * 用户密码
     */
    @TableField("password")
    private String password;

    /**
     * 性别：0 未知， 1男， 1 女
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 生日
     */
    @TableField("birthday")
    private LocalDate birthday;

    /**
     * 最近一次登录时间
     */
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

    /**
     * 最近一次登录IP地址
     */
    @TableField("last_login_ip")
    private String lastLoginIp;

    /**
     * 用户昵称或网络名称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 用户手机号码
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 用户头像图片
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 微信登录openid
     */
    @TableField("weixin_openid")
    private String weixinOpenid;

    /**
     * 微信登录会话KEY
     */
    @TableField("session_key")
    private String sessionKey;

    /**
     * 0 可用, 1 禁用
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 逻辑删除
     */
    @TableField("deleted")
    private int deleted;


}
