package com.ershou.ershou.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2024-11-10
 */
@Data
@TableName("store_cate")
public class StoreCate extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    @TableId(value = "cate_id", type = IdType.AUTO)
    private Integer cateId;

    /**
     * 分类父ID
     */
    private Integer catePid;

    /**
     * 分类名称
     */
    private String cateName;

    private String cateImage;


    private Integer cateStatus;

    /**
     * 分类排序
     */
    private Integer cateOrder;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除
     */
    private Integer delFlag;


}
