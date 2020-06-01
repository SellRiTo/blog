package com.blog.blog.module.sysuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.blog.common.JwtUtil;
import com.blog.blog.module.rabbitmq.Produc;
import com.blog.blog.util.Md5Utils;
import com.blog.blog.common.ResponseEnums;
import com.blog.blog.common.ServerResponse;
import com.blog.blog.config.auth.Token;
import com.blog.blog.config.druiddatasource.DataSource;
import com.blog.blog.config.druiddatasource.DataSourceType;
import com.blog.blog.module.sysuser.entity.LoginParam;
import com.blog.blog.module.sysuser.entity.SysUser;
import com.blog.blog.module.sysuser.mapper.SysUserMapper;
import com.blog.blog.module.sysuser.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.blog.util.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author txr
 * @since 2020-03-31
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private Produc product;

    @Override
    public ServerResponse getUserForId(Long id) {
        SysUser sysUser = baseMapper.selectById(id);
        return new ServerResponse(ResponseEnums.OK,sysUser);
    }

    @Override
    @DataSource(value = DataSourceType.MASTER)
    public ServerResponse getUserForIdFromSlave(Long id) {
        SysUser sysUser = baseMapper.selectById(id);
        return new ServerResponse(ResponseEnums.OK,sysUser);
    }

    @Override
    public byte[] createCaptcha(String sessonId) {
        String captchaCode = CaptchaUtil.generateVerifyCode(4);
        redisTemplate.opsForValue().set("user:captcha:"+sessonId,captchaCode, Duration.ofMinutes(1));

        try {
            return CaptchaUtil.createBase64(100, 40, captchaCode);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("获取验证码失败");
        }
        return null;

    }

    @Override
    public ResponseEntity<Object> login(LoginParam loginParam, HttpSession session) {
        String sessionId = session.getId();
        Object captcha = redisTemplate.opsForValue().get("user:captcha:" + sessionId);
        if (Objects.isNull(captcha)){
             return new ResponseEntity<Object>("验证码已过期",HttpStatus.NOT_FOUND);
        }
        redisTemplate.delete("user:captcha:" + sessionId);
        if (!captcha.equals(loginParam.getCaptcha())){
            return new ResponseEntity<Object>("验证码不正确",HttpStatus.NOT_FOUND);
        }
        SysUser sysUser = this.getOne(new QueryWrapper<SysUser>().eq("is_deleted", 0).eq("account", loginParam.getAccount()));
        if (Objects.isNull(sysUser)){
            return new ResponseEntity<Object>("没有该用户,请重新输入",HttpStatus.NOT_FOUND);
        }
        if (sysUser.getStatus()!=1){
            return new ResponseEntity<Object>("该用户是无效用户,请重新输入",HttpStatus.NOT_FOUND);
        }
        String password = Md5Utils.getMD5(loginParam.getPassword() + sysUser.getSalt());
        if (!password.equals(sysUser.getPassword())){
            return new ResponseEntity<Object>("用户名或者密码错误,请重新输入",HttpStatus.NOT_FOUND);
        }
        Token token = new Token();
        token.setAccount(sysUser.getAccount());
        token.setId(sysUser.getId());
        String tokenStr = JwtUtil.generateToken(token);
        token.setValue(tokenStr);
        redisTemplate.opsForValue().set(sessionId,tokenStr,Duration.ofMinutes(120));
        Map<String,Object> result = new HashMap<>();
        result.put("token",tokenStr);
        return new  ResponseEntity<Object>(result,HttpStatus.OK);


    }

    @Override
    public Integer saveSysUser(SysUser sysUser) {
        String salt = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
        sysUser.setSalt(salt);
        Integer numberCount = baseMapper.selectCount(new QueryWrapper<SysUser>().eq("account", sysUser.getAccount()).eq("is_deleted", 0));
        if (numberCount  >0)
            throw new RuntimeException("该用户已经存在");
        int count = baseMapper.insert(sysUser);
        if (count >0){
           product.send(sysUser);
        }
        return count;
    }
}
