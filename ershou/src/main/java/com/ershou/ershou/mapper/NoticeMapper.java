package com.ershou.ershou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.dto.NoticeDto;
import com.ershou.ershou.domain.po.Notice;
import com.ershou.ershou.domain.query.NoticePageQuery;
import org.apache.ibatis.annotations.Param;

public interface NoticeMapper extends BaseMapper<Notice> {
    Page<NoticeDto> getNoticeList(Page<NoticeDto> page, @Param("noticeQuery") NoticePageQuery noticeQuery);
}
