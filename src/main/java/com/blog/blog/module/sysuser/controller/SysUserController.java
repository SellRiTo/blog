package com.blog.blog.module.sysuser.controller;


import com.blog.blog.common.ResponseEnums;
import com.blog.blog.common.ServerResponse;
import com.blog.blog.config.auth.UserLoginToken;
import com.blog.blog.module.sysuser.entity.LoginParam;
import com.blog.blog.module.sysuser.entity.SysUser;
import com.blog.blog.module.sysuser.service.ISysUserService;
import com.blog.blog.module.sysuser.userdto;
import com.blog.blog.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Handler;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author txr
 * @since 2020-03-31
 */
@RestController
@RequestMapping("/sysuser")
public class SysUserController {

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("selectUserList")
    public ServerResponse selectUserList() {
        List<SysUser> list = sysUserService.list();
        redisUtils.set("sysUser:1", list, 60);
        return new ServerResponse(ResponseEnums.OK, list);

    }

    @GetMapping("deleteRedisSysUser")
    public ServerResponse deleteRedisSysUser() {
        redisUtils.delete("sysUser:1");
        return new ServerResponse(ResponseEnums.OK, null);
    }

    @GetMapping("/exportExcel")
    public List<SysUser> selectUserListForExcel() {
        List<SysUser> list = sysUserService.list();
        return list;
    }


    @UserLoginToken
    @GetMapping("/getUserForId")
    public ServerResponse getUserForId(@Validated userdto dto) {
        return sysUserService.getUserForId(dto.getId());
    }

    @GetMapping("/getUserForIdFromSlave/{id}")
    public ServerResponse getUserForIdFromSlave(@PathVariable("id") Long id) {
        return sysUserService.getUserForIdFromSlave(id);
    }

    @PostMapping("createUser")
    public ServerResponse createUser(@RequestBody @Validated SysUser sysUser){
        boolean save = sysUserService.save(sysUser);
        return save? new ServerResponse(ResponseEnums.OK):new ServerResponse(ResponseEnums.PARAM_);
    }


    /**
     * @Description: 获取验证码
     * @Param:
     */
    @GetMapping("getCaptcha")
    public ResponseEntity<byte[]> createCaptcha(HttpSession httpSession) {
        byte[] captcha = sysUserService.createCaptcha(httpSession.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<byte[]>(captcha, headers, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Validated LoginParam loginParam, HttpSession session){
      return   sysUserService.login(loginParam, session);
    }


    @PostMapping("saveUser")
    public ResponseEntity<Object> saveSysUser(@RequestBody @Validated SysUser sysUser){

        Integer count = sysUserService.saveSysUser(sysUser);
        return  count ==1 ? new ResponseEntity<Object>("新增成功",HttpStatus.OK): new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }
}