package com.ershou.ershou.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author author
 * @since 2025-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_order")
public class StoreOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单号
     */
    private String orderId;

    private Integer goodId;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 用户姓名
     */
    private String realName;

    /**
     * 用户电话
     */
    private String userPhone;

    /**
     * 详细地址
     */
    private Integer addressId;

    /**
     * 跑腿费金额
     */
    private BigDecimal freightPrice;

    /**
     * 订单商品总数
     */
    private Integer totalNum;

    /**
     * 订单总价
     */
    private BigDecimal totalPrice;

    /**
     * 支付跑腿费
     */
    private BigDecimal payPostage;

    /**
     * 支付状态
     */
    private Integer paid;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 支付方式
     */
    private String payType;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 订单状态（0：待发货；1：待收货；2：已收货，待评价；3：已完成；）
     */
    private Integer status;

    /**
     * 快递单号/手机号
     */
    private String deliveryId;

    /**
     * 备注
     */
    private String mark;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 管理员备注
     */
    private String remark;

    /**
     * 商户用户ID
     */
    private Integer merId;

    /**
     * 消息提醒
     */
    private Integer isRemind;

    /**
     * 后台是否删除
     */
    private Boolean isSystemDel;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
