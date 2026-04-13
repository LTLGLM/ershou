package com.ershou.ershou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ershou.ershou.domain.dto.NoticeDto;
import com.ershou.ershou.domain.po.Notice;
import com.ershou.ershou.domain.query.NoticePageQuery;

public interface NoticeService extends IService<Notice> {

    Page<NoticeDto> getNoticeList(Page<NoticeDto> page, NoticePageQuery noticePageQuery);
}
