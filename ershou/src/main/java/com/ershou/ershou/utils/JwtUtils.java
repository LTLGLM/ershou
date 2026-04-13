package com.ershou.ershou.utils;

import com.ershou.ershou.constant.Constants;
import com.ershou.ershou.domain.po.StoreUser;
import com.ershou.ershou.domain.vo.LoginUserVo;
import com.ershou.ershou.exception.GeneralBusinessException;
import com.ershou.ershou.utils.uuid.IdUtils;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtils {

    @Value("${token.header}")
    private String header;

    @Value("${token.secret}")
    private String secret;

    @Value("${token.expireTime}")
    private int expireTime;

    @Autowired
    private RedisCache redisCache;

    /**
     * 创建令牌
     *
     * @param loginUserVo 用户信息
     * @return 令牌
     */
    public String createToken(LoginUserVo loginUserVo) {
        // 生成 UUID 作为 token
        String uuid = IdUtils.fastUUID();
        loginUserVo.setToken(uuid);
        // 刷新 token 的有效期
        refreshToken(loginUserVo);

        // 生成 claims
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_TOKEN_KEY, uuid);

        // 使用 JWT 构建令牌
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date()) // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(expireTime))) // 设置过期时间
                .signWith(SignatureAlgorithm.HS512, secret).compact(); // 使用 HS512 算法加密
        return token;
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUserVo 登录信息
     */
    public void refreshToken(LoginUserVo loginUserVo) {
        // 设置当前时间为登录时间
        loginUserVo.getAdmin().setLastLoginTime(new Date());
        // 生成 Redis 缓存 key
        String toknekey = Constants.LOGIN_TOKEN_KEY + loginUserVo.getToken();
        // 将登录用户信息存入 Redis 并设置过期时间
        redisCache.setCacheObject(toknekey, loginUserVo, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 解析 JWT 令牌
     *
     * @param token JWT 令牌
     * @return 令牌的 Claims 对象
     * @throws io.jsonwebtoken.ExpiredJwtException 如果 token 已过期，会抛出此异常
     * @throws io.jsonwebtoken.JwtException 如果 token 无效或解析失败，会抛出此异常
     */
    public Claims parseJWT(String token) {
        try {
            // 使用 JWT 解析器解析令牌
            Claims claims = Jwts.parser()
                    .setSigningKey(secret) // 设置签名密钥
                    .parseClaimsJws(token) // 注意这里使用 parseClaimsJws
                    .getBody(); // 获取 token 中的负载

            // 如果 token 已过期，会抛出 ExpiredJwtException 异常
            if (isTokenExpired(claims)) {
                throw new ExpiredJwtException(null, null, "token已过期");
            }

            return claims;
        } catch (SignatureException e) {
            throw new GeneralBusinessException("认证失败，token签名无效");
        } catch (MalformedJwtException e) {
            throw new GeneralBusinessException("认证失败，token格式错误");
        } catch (JwtException e) {
            throw new GeneralBusinessException("认证失败，token解析失败");
        }
    }

    /**
     * 判断 token 是否已经过期
     *
     * @param claims 解析后的 token 信息
     * @return true 如果 token 已过期，false 如果 token 有效
     */
    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    public String createUserToken(StoreUser user) {
        // 生成 UUID 作为 token
        String uuid = IdUtils.fastUUID();
        // // 刷新 token 的有效期
        // refreshToken(loginUserVo);
        //
        // // 生成 claims
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_TOKEN_KEY, uuid);

        // 使用 JWT 构建令牌
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date()) // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(expireTime))) // 设置过期时间
                .signWith(SignatureAlgorithm.HS512, secret).compact(); // 使用 HS512 算法加密
        return token;
    }

    public Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret) // 设置密钥
                    .parseClaimsJws(token) // 解析 token
                    .getBody(); // 获取 Claims 载荷
        } catch (ExpiredJwtException e) {
            throw new GeneralBusinessException("Token 已过期");
        } catch (UnsupportedJwtException e) {
            throw new GeneralBusinessException("不支持的 Token 格式");
        } catch (MalformedJwtException e) {
            throw new GeneralBusinessException("Token 格式错误");
        } catch (SignatureException e) {
            throw new GeneralBusinessException("Token 签名无效");
        } catch (IllegalArgumentException e) {
            throw new GeneralBusinessException("Token 不能为空");
        }
    }

}
