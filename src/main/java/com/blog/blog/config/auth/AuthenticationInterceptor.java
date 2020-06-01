package com.blog.blog.config.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.blog.common.JwtUtil;
import com.blog.blog.module.sysuser.entity.SysUser;
import com.blog.blog.module.sysuser.service.ISysUserService;
import jodd.datetime.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @program: blog
 * @description: 拦截器
 * @author: txr
 * @create: 2020-05-25 15:34
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String token = request.getParameter("token");
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
       HandlerMethod method = (HandlerMethod) handler;
        if (method.hasMethodAnnotation(PassToken.class)){
            PassToken passTokenAnnotation = method.getMethodAnnotation(PassToken.class);
           if (passTokenAnnotation.required()){
               return true;
           }
           if (StringUtils.isEmpty(token)){
               return false;
           }
           return  JwtUtil.validateToken(token);
        }

        if (method.hasMethodAnnotation(UserLoginToken.class)){
            UserLoginToken userLoginTokenAnnotation = method.getMethodAnnotation(UserLoginToken.class);
            if (!userLoginTokenAnnotation.required()){
                return true;
            }
            if (StringUtils.isEmpty(token)){
                return false;
            }
            if (JwtUtil.validateToken(token)){
                Token tokenObject = JwtUtil.parseToken(token);
                String sessionId = request.getSession().getId();
                Object tokenStr = redisTemplate.opsForValue().get(sessionId);
                if (Objects.isNull(tokenStr))
                    return false;
                if (!tokenStr.equals(token)){
                    return false;
                }
                Long tokenExpire = redisTemplate.getExpire(sessionId,TimeUnit.SECONDS);
                //重新设置过期时间
                if (tokenExpire < 360L){
                    redisTemplate.opsForValue().set(sessionId,tokenStr, Duration.ofMinutes(120));
                }
                return true;
            }
        }
        return  true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }


}
