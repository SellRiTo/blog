package com.blog.blog.module.service.impl;

import com.blog.blog.module.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

/**
 * @program: blog
 * @description: 验证接口幂等性
 * @author: txr
 * @create: 2020-05-11 17:19
 */
@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    private static  final String LOCK_KEY ="lock:key:";
    private static  final Long LOCK_WAITING=500L;


   @Autowired
   private RedisTemplate<String,Object> redisTemplate;


    @Override
    public Boolean checkIndetment(Object value) {
        Boolean lockFlag = lock(value);

        if (lockFlag){
            log.info("获取到锁开始执行接口");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            unlock(LOCK_KEY);
            return lockFlag;
        }
        return lockFlag;
    }
    
    /**
    *@Description: 设置元素有过期时间
    *@Param: 
    */
    public Boolean setNx(String key ,Object value){
       return redisTemplate.opsForValue().setIfAbsent(key,value, Duration.ofMinutes(2));
    }

    /**
    *@Description: 删除这个锁
    *@Param:
    */
    public Boolean unlock(String key){
        Boolean flag = redisTemplate.delete(key);
        return flag;
    };

    //todo
    // 1.在redis存在一个锁，用来判断是否这个接口已经有请求在执行,如果有自旋一段时间尝试获取锁
    // 2.如果没有,获取一个锁然后判断是否是重复提交,(订单系统一般用订单流水号来确定)
    // 3.如果已经处理过该订单,则返回,没有的话继续执行下面操作
    public Boolean lock(Object value){
       if (Objects.isNull(value)){
           return false;
       }
        long now = System.currentTimeMillis();

        String key = UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
        Boolean flag = setNx(LOCK_KEY , value);
        if (flag){
           return flag;
        }else {
            //如果当期时间小于等待时间,则继续自旋获取锁执行业务
            while (System.currentTimeMillis() < now+LOCK_WAITING){
                Boolean setIfSuccess = setNx(LOCK_KEY , value);
                if (setIfSuccess){
                    return setIfSuccess;
                }
            }
        }
        return false;
    }
}




