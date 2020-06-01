package com.blog.blog.common;

import com.blog.blog.config.auth.Token;
import freemarker.template.utility.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * @program: blog
 * @description:
 * @author: txr
 * @create: 2020-05-25 15:20
 */
public class JwtUtil {
    /**
     * 秘钥
     */
    static final String SECRET = "SR20150715";

    /**
     * 获取token
     *
     * @param token
     * @return
     */
    public static String generateToken(Token token) {
        // 1000 hour
        String jwt = Jwts.builder().setClaims(token.toClaims())
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000_000L))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        // jwt前面一般都会加Bearer
        return "Bearer " + jwt;
    }

    public static Token parseToken(String token) {
        try {
            // parse the Token.
            Map<String, Object> body = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();
            Token tokenObj = new Token();
            tokenObj.fromClaims(body);
            tokenObj.setValue(token);
             return tokenObj;
        } catch (Exception e) {
            return null;
            // throw new IllegalStateException("Invalid Token. " + e.getMessage());
        }
    }

    /**
     * token校验
     *
     * @param token 令牌
     */
    public static boolean validateToken(String token) {
        try {
            // parse the Token.
            Map<String, Object> body = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();
            return true;
        } catch (Exception e) {
            return false;
            // throw new IllegalStateException("Invalid Token. " + e.getMessage());
        }
    }

    /**
     * 获取token对应的claims
     *
     * @param token
     * @return
     */
    public static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace("Bearer ", "")).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
}
