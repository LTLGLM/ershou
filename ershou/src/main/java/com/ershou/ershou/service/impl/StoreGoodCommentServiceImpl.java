package com.ershou.ershou.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ershou.ershou.domain.dto.StoreGoodCommentDto;
import com.ershou.ershou.domain.po.StoreGoodComment;
import com.ershou.ershou.domain.query.StoreGoodCommentPageQuery;
import com.ershou.ershou.mapper.StoreGoodCommentMapper;
import com.ershou.ershou.service.StoreGoodCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreGoodCommentServiceImpl extends ServiceImpl<StoreGoodCommentMapper, StoreGoodComment> implements StoreGoodCommentService {

    @Autowired
    private StoreGoodCommentMapper storeGoodCommentMapper;

    @Override
    public Page<StoreGoodCommentDto> getCommentList(Page<StoreGoodCommentDto> page, StoreGoodCommentPageQuery query) {
        return storeGoodCommentMapper.getCommentList(page, query);
    }

    @Override
    public boolean removeReply(Integer commentId) {
        LambdaUpdateWrapper<StoreGoodComment> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StoreGoodComment::getCommentId, commentId)
                .set(StoreGoodComment::getReplyContent, null)
                .set(StoreGoodComment::getReplyTime, null);
        return update(updateWrapper);
    }
}
