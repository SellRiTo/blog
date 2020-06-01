package com.blog.blog.common.validation;


import org.apache.poi.ss.formula.functions.T;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MobileValidation.class )
public @interface MobileNum {

    String message() default "电话号码规则有误";

    int length() default  11;

    Class<?> [] groups() default {};

    Class<? extends Payload> [] payload() default {};
}
