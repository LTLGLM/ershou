package com.ershou.ershou.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ershou.ershou.domain.dto.NoticeDto;
import com.ershou.ershou.domain.po.Notice;
import com.ershou.ershou.domain.query.NoticePageQuery;
import com.ershou.ershou.mapper.NoticeMapper;
import com.ershou.ershou.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public Page<NoticeDto> getNoticeList(Page<NoticeDto> page, NoticePageQuery noticePageQuery) {
        return noticeMapper.getNoticeList(page, noticePageQuery);
    }
}
