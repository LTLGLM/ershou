package com.ershou.ershou.consumer;

import com.ershou.ershou.domain.po.Notice;
import com.ershou.ershou.websocket.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class NoticeConsumer {

    private static final Logger log = LoggerFactory.getLogger(NoticeConsumer.class);

    private final WebSocketServer webSocketServer;

    public NoticeConsumer(WebSocketServer webSocketServer) {
        this.webSocketServer = webSocketServer;
    }

    @RabbitListener(queues = "manager_notice_queue")
    public void handleNotice(Map<String,Object> notice) {
        if (notice == null) {
            log.error("收到无效的通知消息");
            return;
        }

        List<Integer> adminIds = (List<Integer>) notice.get("adminIds");

        notice.remove("adminIds");

        // 推送通知到所有在线客户端
        webSocketServer.sendMessageToAdmin(adminIds,notice);

        log.info("收到通知消息: {}", notice);
    }

    @RabbitListener(queues = "menu_notice_queue")
    public void handleMenuNotice(Map<String,Object> data) {
        if(data == null){
            log.error("收到无效的菜单消息");
            return;
        }
        List<Integer> adminIds = (List<Integer>) data.get("adminIds");
        data.remove("adminIds");
        webSocketServer.sendMessageToAdmin(adminIds,data);
    }
}
