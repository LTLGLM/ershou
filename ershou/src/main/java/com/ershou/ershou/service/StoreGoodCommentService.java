package com.ershou.ershou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ershou.ershou.domain.dto.StoreGoodCommentDto;
import com.ershou.ershou.domain.po.StoreGoodComment;
import com.ershou.ershou.domain.query.StoreGoodCommentPageQuery;

public interface StoreGoodCommentService extends IService<StoreGoodComment> {

    /**
     * 分页查询评论列表
     * @param page 分页参数
     * @param query 查询参数
     * @return 分页结果
     */
    Page<StoreGoodCommentDto> getCommentList(Page<StoreGoodCommentDto> page, StoreGoodCommentPageQuery query);

    /**
     * 屏蔽回复
     * @param commentId 评论ID
     * @return 是否成功
     */
    boolean removeReply(Integer commentId);
}
