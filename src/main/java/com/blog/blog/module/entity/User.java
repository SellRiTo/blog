package com.blog.blog.module.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: blog
 * @description: 测试实体类
 * @author: txr
 * @create: 2020-03-06 15:01
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private Integer id;
    private String username;
    private Integer age;
    private Integer sex;

}
