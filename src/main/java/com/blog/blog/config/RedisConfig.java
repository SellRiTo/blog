package com.blog.blog.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @program: blog
 * @description: redis配置类
 * @author: txr
 * @create: 2020-03-06 14:26
 */
@Configuration
@EnableCaching
public class RedisConfig {


    @Bean
    @ConditionalOnClass(RedisOperations.class)
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){

         RedisTemplate<String,Object> template = new RedisTemplate<>();
         template.setConnectionFactory(redisConnectionFactory);

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(mapper);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //redis 的key用字符串的序列号方式
        template.setKeySerializer(stringRedisSerializer);
        //redis hash的key序列化方式使用字符串的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        //redis 的value的序列化方式使用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // redis的hash的value序列化采用jackson
        template.afterPropertiesSet();
        return template;
    }
}
