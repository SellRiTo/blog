package com.blog.blog.module.controller;

import com.alibaba.excel.EasyExcel;
import com.blog.blog.config.execl.ExcelUtils;
import com.blog.blog.module.entity.User;
import com.blog.blog.util.CaptchaUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: blog
 * @description: 测试
 * @author: txr
 * @create: 2020-03-03 11:15
 */
@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("/sayHello")
    public String SayHello(){
        System.out.println("hello!!!");
        return "success";
    }

    @GetMapping("/export")
    public List<User> exprotExcel(){
        List<User> dataList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setId(i);
            user.setAge(i);
            user.setUsername("ce"+i);
            dataList.add(user);
        }
        return dataList;

    }

    /**
    *@Description: 导出测试
    *@Param:
    */
    @GetMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        List<User> dataList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setId(i);
            user.setAge(i);
            user.setUsername("ce"+i);
            user.setSex(0);
            dataList.add(user);
        }
        String[] headers = {"姓名","年龄","性别"};
        String[] fields = {"username","age","sex"};
        ExcelUtils.exportToResp("测试文件",dataList,headers,fields);

    }


    @GetMapping("/throwExection")
    public void throwExection(){
       throw new ResponseStatusException(HttpStatus.NOT_FOUND,"没有找打");
    }
}
