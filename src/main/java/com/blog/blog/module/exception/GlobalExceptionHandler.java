package com.blog.blog.module.exception;

import com.blog.blog.common.ResponseEnums;
import com.blog.blog.common.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;
import java.io.UnsupportedEncodingException;

/**
 * @program: blog
 * @description: 全局异常捕获
 * @author: txr
 * @create: 2020-05-14 11:23
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * ValidationException
     */
    @ExceptionHandler(ValidationException.class)
    public ServerResponse handleValidationException(ValidationException e) {
        log.info("====ValidationException.class===");
        log.info(e.getMessage(), e);

        return new ServerResponse(ResponseEnums.PARAM_, e.getCause().getMessage());
    }

    /**
     * ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ServerResponse handleConstraintViolationException(ConstraintViolationException e) {
        log.info("====ConstraintViolationException.class===");
        log.info(e.getMessage(), e);
        return new ServerResponse(ResponseEnums.PARAM_, e.getMessage());
    }

    /**
     * ConstraintViolationException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ServerResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("====MethodArgumentNotValidException.class===");
        log.info(e.getMessage(), e);
        return new ServerResponse(ResponseEnums.PARAM_, e.getMessage());
    }
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handlerBindException(BindException e) {
        log.error(e.getMessage(), e);
        String errorMessage =getErrorMessage(e.getBindingResult());
        return new ResponseEntity<Object>(errorMessage, HttpStatus.NOT_FOUND);
    }

    public  String getErrorMessage(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                sb.append(new String(error.getDefaultMessage()));
            }
            return sb.toString();
        }
        return null;
    }
}
