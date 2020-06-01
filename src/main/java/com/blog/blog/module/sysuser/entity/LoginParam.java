package com.blog.blog.module.sysuser.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @program: blog
 * @description: 登录参数
 * @author: txr
 * @create: 2020-05-25 17:06
 */
@Data
public class LoginParam {

    @NotBlank(message = "账号不能为空")
   private String account;
    @NotBlank(message = "密码不能为空")
   private String password;
    @NotBlank(message = "验证码不能为空")
    @Length(max = 4,message = "验证码长度不能超过4")
   private String captcha;
}
