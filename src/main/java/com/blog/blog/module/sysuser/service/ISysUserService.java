package com.blog.blog.module.sysuser.service;

import com.blog.blog.common.ServerResponse;
import com.blog.blog.module.sysuser.entity.LoginParam;
import com.blog.blog.module.sysuser.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author txr
 * @since 2020-03-31
 */
public interface ISysUserService extends IService<SysUser> {
    ServerResponse getUserForId(Long id);
    ServerResponse getUserForIdFromSlave(Long id);
    byte[] createCaptcha(String sessionId);
    ResponseEntity<Object> login(LoginParam loginParam, HttpSession session);
    Integer saveSysUser(SysUser sysUser);
}
