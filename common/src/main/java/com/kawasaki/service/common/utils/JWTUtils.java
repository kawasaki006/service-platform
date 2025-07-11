package com.kawasaki.service.common.utils;

import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.exception.BizExceptionCodeEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class JWTUtils {
    private static final String SECRET = "poyopoyopoyopoyopoyopoyopoyopoyo";
    private static final long EXPIRATION = 3600_000L;
    public static String generateToken(String subject, Map<String, String> payload) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
        return Jwts.builder()
                .setSubject(subject)
                .setClaims(payload)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    public static Claims verifyToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new BizException(BizExceptionCodeEnum.BAD_TOKEN);
        }
    }
}
