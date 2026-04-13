package com.ershou.ershou.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

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
 * @since 2024-11-11
 */
@Data
// @EqualsAndHashCode(callSuper = false)
// @Accessors(chain = true)
@TableName("store_catesize")
public class StoreCatesize extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分类规格ID
     */
    @TableId(value = "size_id", type = IdType.AUTO)
    private Integer sizeId;

    /**
     * 分类ID
     */
    @TableField("cate_id")
    private Integer cateId;

    /**
     * 分类规格名称
     */
    @TableField("size_name")
    private String sizeName;

    /**
     * 是否删除
     */
    @TableField("del_flag")
    private Integer delFlag;


}
