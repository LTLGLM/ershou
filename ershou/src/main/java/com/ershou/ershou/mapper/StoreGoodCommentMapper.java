package com.ershou.ershou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.dto.StoreGoodCommentDto;
import com.ershou.ershou.domain.po.StoreGoodComment;
import com.ershou.ershou.domain.query.StoreGoodCommentPageQuery;
import org.apache.ibatis.annotations.Param;

public interface StoreGoodCommentMapper extends BaseMapper<StoreGoodComment> {

    /**
     * 分页查询评论列表
     * @param page 分页参数
     * @param query 查询参数
     * @return 分页结果
     */
    Page<StoreGoodCommentDto> getCommentList(Page<StoreGoodCommentDto> page, @Param("query") StoreGoodCommentPageQuery query);
}
