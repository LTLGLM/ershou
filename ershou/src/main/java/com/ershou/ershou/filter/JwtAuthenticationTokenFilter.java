package com.ershou.ershou.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSON;
import com.ershou.ershou.constant.Constants;
import com.ershou.ershou.constant.HttpStatus;
import com.ershou.ershou.domain.vo.LoginUserVo;
import com.ershou.ershou.exception.GeneralBusinessException;
import com.ershou.ershou.utils.AjaxResult;
import com.ershou.ershou.utils.JwtUtils;
import com.ershou.ershou.utils.RedisCache;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取 token
        String token = request.getHeader("Token");
        if (StringUtils.isEmpty(token)) {
            filterChain.doFilter(request, response);  // 没有 token 也放行
            return;
        }

        try {
            // 解析 token
            Claims claims = jwtUtils.parseJWT(token);
            String uuid = (String) claims.get(Constants.LOGIN_TOKEN_KEY);

            // 从 Redis 获取用户信息
            LoginUserVo loginUserVo = redisCache.getCacheObject(Constants.LOGIN_TOKEN_KEY + uuid);

            if (loginUserVo == null) {
                throw new GeneralBusinessException("用户未登录或登录信息已过期");
            }

            // 刷新token时间
            jwtUtils.refreshToken(loginUserVo);

            // 设置认证上下文
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserVo, null, loginUserVo.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (GeneralBusinessException e) {
            // 处理认证失败的异常
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(new AjaxResult(HttpStatus.UNAUTHORIZED, e.getMessage())));
            return;  // 结束请求
        } catch (JwtException e) {
            // 处理 JWT 解析异常
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(new AjaxResult(HttpStatus.UNAUTHORIZED, "认证失败，token无效")));
            return;  // 结束请求
        }

        filterChain.doFilter(request, response);
    }
}

