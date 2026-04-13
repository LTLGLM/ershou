package com.ershou.ershou.exception;

import com.alibaba.fastjson2.JSON;
import com.ershou.ershou.constant.HttpStatus;
import com.ershou.ershou.utils.AjaxResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证异常处理器
 */
@Component
public class AuthticationEntryPointImpl implements AuthenticationEntryPoint{
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    //     处理异常
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(new AjaxResult(HttpStatus.UNAUTHORIZED,"用户认证失败请查询登录")));
    }
}
