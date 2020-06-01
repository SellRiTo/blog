package com.blog.blog.common.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @program: smart-server
 * @description:
 * @author: txr
 * @create: 2020-05-20 11:32
 */



@Documented
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DefindRegValidatorChecker.class)
public @interface DefindRegValidator {

    String message() default "参数格式不正确";

    /**
    *@Description: 默认是判断ip格式对的正则
    *@Param:
    */
    String regexpRef() default "default";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
