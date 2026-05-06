package com.hq.common.filter;

import static com.hq.common.Const.Jwt.ALGORITHM;
import static com.hq.common.Const.Jwt.EXPIRE;
import static com.hq.common.Const.Jwt.USER_ID;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 高振中
 * @summary 【JWT创建与解析】工具
 * @date 2024-05-10 21:44:15
 **/
@Slf4j
public final class JwtTools {
    private JwtTools() {
    }

    public static String createToken(Long userId) {
        // 指定:标识字段,过期时间,生成算法及签名
        return JWT.create().withClaim(USER_ID, userId).withExpiresAt(LocalDateTime.now().plusDays(EXPIRE).toInstant(ZoneOffset.of("+8"))).sign(ALGORITHM);
    }

    public static boolean verify(String token) {
        try {
            JWT.require(ALGORITHM).build().verify(token);
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
            return false;
        }
        return true;
    }

    public static Long userId(String token) {
        DecodedJWT verify = JWT.require(ALGORITHM).build().verify(token);
        return verify.getClaim(USER_ID).asLong();
    }
}
