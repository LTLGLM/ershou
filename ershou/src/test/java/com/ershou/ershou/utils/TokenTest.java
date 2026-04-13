package com.ershou.ershou.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ershou.ershou.constant.Constants;
import com.ershou.ershou.domain.po.Admin;
import com.ershou.ershou.domain.vo.LoginUserVo;
import com.ershou.ershou.mapper.AdminMapper;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TokenTest {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisCache redisCache;
    @Test
    public void testToken() {
        String username = "admin";
        Admin user = adminMapper.selectOne(new LambdaQueryWrapper<Admin>().eq(username != null,Admin::getUsername,username));
        LoginUserVo loginUserVo = new LoginUserVo(user);
        String token = jwtUtils.createToken(loginUserVo);
        System.out.println("Token: " + token);

        Claims claims = jwtUtils.parseJWT(token);
        String uuid = (String) claims.get(Constants.LOGIN_TOKEN_KEY);

    }
}
