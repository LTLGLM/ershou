package com.ershou.ershou.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 校区信息表
 * </p>
 *
 * @author author
 * @since 2025-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("store_campus")
public class StoreCampus implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 校区唯一标识
     */
    @TableId(value = "campus_id", type = IdType.AUTO)
    private Integer campusId;

    /**
     * 校区名称
     */
    private String campusName;

    /**
     * 校区图片
     */
    private String campusImage;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 逻辑删除
     */
    private Boolean deleted;


}
