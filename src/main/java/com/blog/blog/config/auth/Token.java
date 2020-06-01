package com.blog.blog.config.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: blog
 * @description:
 * @author: txr
 * @create: 2020-05-25 15:22
 */
@Data

public class Token implements Serializable {

    private static final long serialVersionUID = 2312425L;

    private Long id;
    private String account;
    private String value;// token 的值
    private Integer exp;// 超时时间

    public Map<String,Object> toClaims(){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",id);
        claims.put("account",account);

        return claims;
    }

    public  Token fromClaims(Map<String, Object> claims) {
        // FIXME: 为简化，先不做类型判断，直接强制转型
        this.id = Long.valueOf(String.valueOf(claims.get("id")));
        this.account = (String) claims.get("account");
        this.exp = (Integer) claims.get("exp");

        return this;
    }
}
