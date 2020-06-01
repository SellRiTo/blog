package com.blog.blog.config.druiddatasource;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @program: blog
 * @description: 多数据源切面处理
 * @author: txr
 * @create: 2020-04-08 18:03
 */

@Aspect
@Order(1)
@Component
@Slf4j
public class DataSourceAspect {

    @Autowired
    private DruidProperties druidProperties;

    @Pointcut("execution(* com.blog.blog..impl.*.*(..))")
    public void dsPointCut(){

    }

    @Around("dsPointCut()")
    public Object around(ProceedingJoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DataSource dataSource = method.getAnnotation(DataSource.class);

        if (!Objects.isNull(dataSource)){
            log.info("切换数据源为{}",dataSource.value().name());
            DynamicDataSourceContextHolder.setDataSourceType(dataSource.value().name());
        } else if (druidProperties.slaveEnable){
           if (isSlave(method.getName())){
               log.info("切换数据源为{}",DataSourceType.SLAVE.name());
               DynamicDataSourceContextHolder.setDataSourceType(DataSourceType.SLAVE.name());
           }
        }
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }finally {
            log.info("清除数据源{}");
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
    }

    public  boolean isSlave(String methodName){
        //当方法为查询方法是走从库
        return  StringUtils.startsWithAny(methodName,new String[]{"find","get","select"});

    }
}
