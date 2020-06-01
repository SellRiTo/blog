package com.blog.blog.common;

/*
 * @author JavisChen
 * @desc 响应常量管理
 * @date 2018/5/29 下午2:55
 */
public enum ResponseEnums {

    OK(0, "ok"),
    ERROR(-1, "error"),
    CAPTCHA_ERROR(201, "验证码输入错误"),
    PARAM_(400, "error"),
    USER_LOCKED(403, "用户已被锁定，请联系管理员"),
    USER_NOT_LOGIN(403, "用户未登录或会话已过期");

    private Integer code;
    private String msg;

    ResponseEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
