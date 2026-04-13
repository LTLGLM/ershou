package com.ershou.ershou.domain.dto;

import com.ershou.ershou.domain.po.StoreGoodComment;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StoreGoodCommentDto extends StoreGoodComment {

    /**
     * 商品名称
     */
    private String goodName;

    /**
     * 商品图片
     */
    private String image;

    /**
     * 买家昵称
     */
    private String nickname;
}
