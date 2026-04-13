package com.ershou.ershou.domain.query;

import com.ershou.ershou.domain.Pagequery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StoreGoodCommentPageQuery extends Pagequery {

    /**
     * 商品名称
     */
    private String goodName;

    /**
     * 买家昵称
     */
    private String nickname;

    /**
     * 是否已回复
     */
    private Boolean isReplied;
}
