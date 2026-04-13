package com.ershou.ershou.config;

import com.ershou.ershou.interceptor.WebSocketHandshakeInterceptor;
import com.ershou.ershou.websocket.WebSocketServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket  // 启用 WebSocket 配置
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketServer webSocketServer;
    private final WebSocketHandshakeInterceptor webSocketHandshakeInterceptor;

    // 修改构造器注入 WebSocketHandshakeInterceptor
    public WebSocketConfig(WebSocketServer webSocketServer, WebSocketHandshakeInterceptor webSocketHandshakeInterceptor) {
        this.webSocketServer = webSocketServer;
        this.webSocketHandshakeInterceptor = webSocketHandshakeInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketServer, "/websocket")  // 设置 WebSocket 处理器
                .setAllowedOrigins("*")  // 允许跨域
                .addInterceptors(webSocketHandshakeInterceptor);  // 使用构造器注入的拦截器
    }
}

