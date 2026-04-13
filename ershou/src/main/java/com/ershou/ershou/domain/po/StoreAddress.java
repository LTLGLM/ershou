package com.ershou.ershou.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 收货地址表
 * </p>
 *
 * @author author
 * @since 2025-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_address")
public class StoreAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "address_id", type = IdType.AUTO)
    private Integer addressId;

    /**
     * 收货人名称
     */
    private String name;

    /**
     * 用户表的用户ID
     */
    private Integer userId;

    /**
     * 校园楼ID
     */
    private Integer floorId;

    /**
     * 详细收货地址
     */
    private String addressDetail;

    /**
     * 手机号码
     */
    private String tel;

    /**
     * 是否默认地址
     */
    private Integer isDefault;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 逻辑删除
     */
    private Boolean deleted;

    /**
     * 校区ID
     */
    private Integer campusId;


}
