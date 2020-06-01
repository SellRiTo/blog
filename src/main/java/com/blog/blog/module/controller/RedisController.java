package com.blog.blog.module.controller;

import com.alibaba.fastjson.JSON;
import com.blog.blog.module.entity.User;
import com.blog.blog.module.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: blog
 * @description:
 * @author: txr
 * @create: 2020-03-06 15:08
 */
@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisController {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private RedisService redisService;

    @GetMapping("/opeRedis")
    public void operationRedis(){
        String key ="user:1";
         redisTemplate.opsForValue().set(key,new User(1,"唐贤锐1",26,1));
         User user = (User) redisTemplate.opsForValue().get(key);

        System.out.println("=============="+ user.getUsername());
    }


    /*
    * 以下方法是为了实现接口的幂等性
    *
    * */
    @GetMapping("checkIndetment")
    public ResponseEntity<Object> checkIndetment(){
        Object value = 1;
        Boolean flag = redisService.checkIndetment(value);
        if (flag){
            log.info("获取锁成功,执行业务操作成功");
            return ResponseEntity.ok("执行成功过");
        }else {
            log.info("获取锁失败,不执行业务操作");
            return new ResponseEntity<Object>("不执行业务",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
