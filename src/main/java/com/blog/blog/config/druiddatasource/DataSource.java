package com.blog.blog.config.druiddatasource;


import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented

/**
*@Description: 自定义数据源切换注解
*@Param:
*/
public @interface DataSource {
    public DataSourceType value() default DataSourceType.MASTER;
}
