package com.blog.blog.common.validation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @program: smart-server
 * @description: 自定义ip注解
 * @author: txr
 * @create: 2020-05-20 11:59
 */
public class DefindRegValidatorChecker implements ConstraintValidator<DefindRegValidator,String> {

    private  DefindRegValidator defindRegValidator;

    @Override
    public void initialize(DefindRegValidator constraintAnnotation) {
        defindRegValidator = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(value)){
            return false;
        }
        String defindReg = defindRegValidator.regexpRef();
        if (StringUtils.isEmpty(defindReg)){
            throw  new RuntimeException("正则校验器不能没有正则定义");
        }
        value = value.trim();
        if (StringUtils.isEmpty(value)){
            return false;
        }
        return Pattern.matches(defindReg, value);
    }
}
