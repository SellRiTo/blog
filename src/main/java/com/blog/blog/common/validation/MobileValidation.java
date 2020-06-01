package com.blog.blog.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @program: blog
 * @description: 号码校验器
 * @author: txr
 * @create: 2020-05-14 10:51
 */
public class MobileValidation implements ConstraintValidator<MobileNum,String>{
    @Override
    public void initialize(MobileNum constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        String reg = "^[1][3,4,5,6,7,8,9][0-9]{9}$";
        return reg.matches(value);
    }
}
