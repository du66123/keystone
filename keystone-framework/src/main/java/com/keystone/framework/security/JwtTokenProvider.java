package com.keystone.framework.security;

import com.keystone.framework.config.properties.TokenProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * JWT Token 工具类
 */
@Component
public class JwtTokenProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    private final TokenProperties tokenProperties;
    private final SecretKey secretKey;

    public JwtTokenProvider(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
        this.secretKey = Keys.hmacShaKeyFor(tokenProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 创建 JWT Token
     *
     * @param claims 自定义声明
     * @return Token 字符串
     */
    public String createToken(Map<String, Object> claims) {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + (long) tokenProperties.getExpireMinutes() * 60 * 1000;

        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .claims(claims)
                .issuedAt(new Date(nowMillis))
                .expiration(new Date(expMillis))
                .signWith(secretKey)
                .compact();
    }

    /**
     * 解析 Token 获取 Claims
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 从请求头中提取 Token（去除 Bearer 前缀）
     */
    public String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(tokenProperties.getPrefix())) {
            return bearerToken.substring(tokenProperties.getPrefix().length());
        }
        return null;
    }

    /**
     * 验证 Token 是否有效
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            log.warn("JWT Token 验证失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 获取 Token 中的用户标识
     */
    public String getUserKey(String token) {
        Claims claims = parseToken(token);
        return claims.get("user_key", String.class);
    }
}
