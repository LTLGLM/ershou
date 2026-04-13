package com.ershou.ershou.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sys_notice")
public class Notice extends Model implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "notice_id")
    private Integer noticeId;

    private String noticeTitle;

    private String noticeContent;

    private Integer noticeStatus;

    private Integer createBy;

    private Date createTime;

    private Integer updateBy;

    private Date updateTime;

    private Integer delFlag;

    private String remark;
}
