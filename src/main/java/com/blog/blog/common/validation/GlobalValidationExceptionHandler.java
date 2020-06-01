package com.blog.blog.common.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @program: smart-server
 * @description: 统一全局处理
 * @author: txr
 * @create: 2020-05-22 14:06
 */
@RestControllerAdvice
public class GlobalValidationExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handle(MethodArgumentNotValidException ex) {
        return this.processBindingResult(ex.getBindingResult());
    }

    @ExceptionHandler({BindException.class})
    public ResponseEntity<Object> handle(BindException ex) {
        return this.processBindingResult(ex.getBindingResult());
    }

    private ResponseEntity<Object> processBindingResult(BindingResult result) {
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            StringBuilder respMessage = new StringBuilder();
            fieldErrors.forEach((error) -> {
                respMessage.append(",").append(error.getDefaultMessage());
            });

            return  new  ResponseEntity<Object>(respMessage.toString().substring(1),HttpStatus.NOT_FOUND);
        } else {
            return  new ResponseEntity<Object>("校验参数失败",HttpStatus.NOT_FOUND);
        }
    }

}
