package com.ershou.ershou.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 行政区域表
 * </p>
 *
 * @author author
 * @since 2024-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_floor")
public class StoreFloor extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "floor_id", type = IdType.AUTO)
    private Integer floorId;

    /**
     * 校园楼名称
     */
    @TableField("floor_name")
    private String floorName;

    /**
     * 校园楼图片
     */
    @TableField("floor_image")
    private String floorImage;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 逻辑删除
     */
    @TableField("deleted")
    private Boolean deleted;


}
