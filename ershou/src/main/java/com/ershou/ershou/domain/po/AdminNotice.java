package com.ershou.ershou.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2025-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_admin_notice")
public class AdminNotice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "admin_id", type = IdType.INPUT)
    private Integer adminId;

    /**
     * 通知id
     */
    @TableField("notice_id")
    private Integer noticeId;

    /**
     * 是否确认
     */
    @TableField("is_confirm")
    private Integer isConfirm;

    @TableField("confirm_time")
    private Date confirmTime;

    @TableField("del_flag")
    private Integer delFlag;
}
