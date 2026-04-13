package com.ershou.ershou.controller;

import com.ershou.ershou.domain.dto.AdminDto;
import com.ershou.ershou.domain.po.Admin;
import com.ershou.ershou.domain.po.Notice;
import com.ershou.ershou.service.AdminService;
import com.ershou.ershou.utils.BeanCopyUtils;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;

@SpringBootTest
class NoticeControllerTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testAddNotice() {
        Notice notice = new Notice();
        notice.setNoticeTitle("测试通知");
        notice.setNoticeContent("测试通知内容");
        notice.setNoticeStatus(1);

        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", notice);
        
        
    }
}