package com.ershou.ershou.websocket;

import com.alibaba.fastjson2.JSON;
import com.ershou.ershou.domain.vo.LoginUserVo;
import com.ershou.ershou.utils.JwtUtils;
import com.ershou.ershou.utils.SecurityAdminUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author CoderJia
 * @create 2024/12/15 下午 08:21
 * @Description
 **/
@Component
@Slf4j
public class WebSocketServer extends TextWebSocketHandler {

    // 用于存储WebSocket会话
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LoginUserVo loginUserVo = (LoginUserVo) session.getAttributes().get("user");
        if(loginUserVo == null){
            session.close();
            return;
        }
        String adminId = String.valueOf(loginUserVo.getAdmin().getAdminId());
        // 检查是否重复连接了
        if(!sessions.containsKey(adminId)){
            sessions.put(adminId, session);
        }else {
            log.error("WebSocket 重复连接：{}", adminId);
            return;
        }

        log.info(String.valueOf(sessions.size()));
        log.info("WebSocket连接建立成功：{}", adminId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("收到消息：{}", payload);

        // 发送回复消息
        HashMap<String, Object> sendMessage = new HashMap<>();
        sendMessage.put("message","服务器收到消息");
        sendMessage.put("status","200");
        sendMessage.put("data",payload);
        String jsonMessage = JSON.toJSONString(sendMessage);
        session.sendMessage(new TextMessage(jsonMessage));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        LoginUserVo loginUserVo = (LoginUserVo) session.getAttributes().get("user");
        if(loginUserVo == null){
            session.close();
            return;
        }
        String adminId = String.valueOf(loginUserVo.getAdmin().getAdminId());
        sessions.remove(adminId);
        log.info("WebSocket连接关闭：{}", adminId);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("WebSocket传输错误", exception);
    }

    // 广播消息给所有连接的客户端
    public void broadcastMessage(Object message) {

        String jsonMessage = JSON.toJSONString(message);

        sessions.values().forEach(session -> {
            try {
                session.sendMessage(new TextMessage(jsonMessage));
                log.info("广播成功");
            } catch (IOException e) {
                log.error("广播消息失败", e);
            }
        });
    }

    // 广播消息给指定的客户端
    public void sendMessageToAdmin(List<Integer> adminIds,Object message){

        String jsonMessage = JSON.toJSONString(message);

        sessions.forEach((userId,session) -> {
            if(adminIds.contains(Integer.parseInt(userId))){
                try {
                    session.sendMessage(new TextMessage(jsonMessage));
                    log.info("向指定管理员广播消息成功");
                } catch (IOException e) {
                    log.error("向指定管理员广播消息失败", e);
                }
            }
        });
    }
}
