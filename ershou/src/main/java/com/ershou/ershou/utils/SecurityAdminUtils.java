package com.ershou.ershou.utils;

import com.ershou.ershou.domain.vo.LoginUserVo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityAdminUtils {
    public static LoginUserVo getAdmin(){
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return (LoginUserVo) authentication.getPrincipal();
    }

    public static void setOrUpdateAdmin(LoginUserVo loginUserVo) {
        UsernamePasswordAuthenticationToken oldAuth =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        // 确保旧的 authentication 不是 null，并且可以获取 details
        Object details = (oldAuth != null) ? oldAuth.getDetails() : null;

        // 创建新的 Authentication 对象，覆盖原来的
        UsernamePasswordAuthenticationToken newAuth =
                new UsernamePasswordAuthenticationToken(loginUserVo, loginUserVo.getPassword(), loginUserVo.getAuthorities());

        newAuth.setDetails(details); // 继承原来的 details

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

}
