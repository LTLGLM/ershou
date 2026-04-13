package com.ershou.ershou.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商品评论表
 * </p>
 *
 * @author author
 * @since 2024-02-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_good_comment")
public class StoreGoodComment extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论ID
     */
    @TableId(value = "comment_id", type = IdType.AUTO)
    private Integer commentId;

    /**
     * 买家ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 商品ID
     */
    @TableField("good_id")
    private Integer goodId;

    /**
     * 评论内容
     */
    @TableField("content")
    private String content;

    /**
     * 卖家回复内容
     */
    @TableField("reply_content")
    private String replyContent;

    /**
     * 回复时间
     */
    @TableField("reply_time")
    private Date replyTime;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 是否显示(0:隐藏 1:显示)
     */
    @TableField("is_show")
    private Integer isShow;
}
