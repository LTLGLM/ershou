package com.ershou.ershou.exception;

import com.alibaba.fastjson2.JSON;
import com.ershou.ershou.utils.AjaxResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权失败处理器
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");

        String requestURI = request.getRequestURI();
        String errorMessage = getErrorMessageForUri(requestURI);

        response.getWriter().write(JSON.toJSONString(AjaxResult.error(errorMessage)));
    }

    private String getErrorMessageForUri(String requestURI) {
        // 可扩展
        // 根据请求的 URI 返回不同的错误信息
        if (requestURI.contains("/list")) {
            return "您没有权限查询该页面的数据";
        }
        return "您的权限不足";
    }
}
