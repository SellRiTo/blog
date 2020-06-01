package com.blog.blog.module.sysuser;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: blog
 * @description: 测试
 * @author: txr
 * @create: 2020-05-18 11:03
 */
@Data
public class userdto {

    @NotNull(message = "id不能为空")
    private Long id;

}
