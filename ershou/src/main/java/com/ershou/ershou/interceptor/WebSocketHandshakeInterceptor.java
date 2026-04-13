package com.ershou.ershou.interceptor;

import com.ershou.ershou.constant.Constants;
import com.ershou.ershou.domain.vo.LoginUserVo;
import com.ershou.ershou.exception.GeneralBusinessException;
import com.ershou.ershou.utils.JwtUtils;
import com.ershou.ershou.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.io.IOException;
import java.util.Map;
@Component
public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    private final RedisCache redisCache;
    private final JwtUtils jwtUtils;

    // 使用构造器注入依赖
    public WebSocketHandshakeInterceptor(RedisCache redisCache, JwtUtils jwtUtils) {
        this.redisCache = redisCache;
        this.jwtUtils = jwtUtils;
    }


    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // 获取 Token（从请求的 URL 查询参数中）
        String token = request.getURI().getQuery();

        // 判断 token 是否为空
        if (StringUtils.isEmpty(token) || !token.startsWith("token=")) {
            sendErrorResponse(response, "Token为空，未授权", HttpStatus.UNAUTHORIZED);
            return false;  // 拦截连接，返回 false
        }

        // 提取 token
        token = token.substring("token=".length());

        LoginUserVo loginUserVo = null;

        try {
            // 解析 Token
            Claims claims = jwtUtils.parseJWT(token);
            String uuid = (String) claims.get(Constants.LOGIN_TOKEN_KEY);

            // 从 Redis 获取用户信息
            loginUserVo = redisCache.getCacheObject(Constants.LOGIN_TOKEN_KEY + uuid);

            if (loginUserVo == null) {
                throw new GeneralBusinessException("用户未登录或登录信息已过期");
            }

            // 校验 Token 是否过期
            if (jwtUtils.isTokenExpired(claims)) {
                throw new GeneralBusinessException("Token 已过期");
            }

        } catch (Exception e) {
            // 解析错误或 Token 非法
            sendErrorResponse(response, "Token非法", HttpStatus.UNAUTHORIZED);
            return false;  // 拦截连接，返回 false
        }

        attributes.put("user",loginUserVo);
        // Token 验证通过后，继续执行握手
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    // 发送错误响应
    private void sendErrorResponse(ServerHttpResponse response, String errorMessage, HttpStatus status) {
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        try {
            response.getBody().write(("{\"message\":\"" + errorMessage + "\"}").getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error writing response body", e);
        }
    }
}
