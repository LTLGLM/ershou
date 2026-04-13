package com.ershou.ershou.domain.po;

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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_sizevalue")
public class StoreSizevalue extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 规格值ID
     */
    @TableId(value = "sizevalue_id", type = IdType.AUTO)
    private Long sizevalueId;

    /**
     * 分类规格ID
     */
    private Long sizeId;

    /**
     * 规格值
     */
    private String sizeValue;

    /**
     * 是否删除
     */
    private Long delFlag;


}
