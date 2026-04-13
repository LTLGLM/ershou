package com.ershou.ershou.domain.po;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author author
 * @since 2024-11-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_good")
public class StoreGood extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @TableId(value = "good_id", type = IdType.AUTO)
    private Integer goodId;

    /**
     * 商品所属用户
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 商户Id(0为总后台管理员创建,不为0的时候是商户后台创建)
     */
    @TableField("mer_id")
    private Integer merId;

    /**
     * 商品图片
     */
    @TableField("image")
    private String image;

    /**
     * 商品名称
     */
    @TableField("good_name")
    private String goodName;

    /**
     * 轮播图
     */
    @TableField("slider_image")
    private String sliderImage;

    /**
     * 商品简介
     */
    @TableField("good_info")
    private String goodInfo;

    /**
     * 关键字
     */
    @TableField("keyword")
    private String keyword;

    /**
     * 分类id
     */
    @TableField("cate_id")
    private Integer cateId;

    /**
     * 商品价格
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 原价
     */
    @TableField("original_price")
    private BigDecimal originalPrice;

    /**
     * 跑腿费
     */
    @TableField("postage")
    private BigDecimal postage;

    /**
     * 单位名
     */
    @TableField("unit_name")
    private String unitName;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 库存
     */
    @TableField("stock")
    private Integer stock;

    /**
     * 状态（0：未上架，1：上架）
     */
    @TableField("is_show")
    private Boolean isShow;

    /**
     * 添加时间
     */
    @TableField("add_time")
    private Date addTime;

    /**
     * 是否包邮
     */
    @TableField("is_postage")
    private Integer isPostage;

    /**
     * 是否删除
     */
    @TableField("is_del")
    private Integer isDel;

    /**
     * 浏览量
     */
    @TableField("browse")
    private Integer browse;


}
