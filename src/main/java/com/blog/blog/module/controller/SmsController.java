package com.blog.blog.module.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.blog.blog.config.SmsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Random;

/**
 * @program: blog
 * @description: 短信接口
 * @author: txr
 * @create: 2020-03-12 10:05
 */
@RestController
@RequestMapping("/sms")
@Slf4j
public class SmsController {

    @Autowired
    private SmsConfig smsConfig;


    @GetMapping("/send")
    public Boolean  sendSms(@RequestParam(value = "phone")String phone){

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsConfig.getAccessKeyId(), smsConfig.getAccessSecret());
        DefaultAcsClient defaultAcsClient = new DefaultAcsClient(profile);
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setMethod(MethodType.POST);
        commonRequest.setDomain("dysmsapi.aliyuncs.com");
        commonRequest.setVersion("2017-05-25");
        commonRequest.setAction("SendSms");
        commonRequest.putQueryParameter("RegionId", "cn-hangzhou");
        commonRequest.putQueryParameter("PhoneNumbers", phone);
        commonRequest.putQueryParameter("SignName", smsConfig.getSignName());
        commonRequest.putQueryParameter("TemplateCode", smsConfig.getTemplateCode());
        JSONObject object=new JSONObject();
        String randCode=getRandCode(6);
        log.info("验证码为：{}",randCode);
        object.put("code",randCode);
        commonRequest.putQueryParameter("TemplateParam", object.toJSONString());
        try {
            CommonResponse response = defaultAcsClient.getCommonResponse(commonRequest);
            log.info(response.getData());
            return true;
        } catch (Exception e) {
            log.error("{}",e);
        }
        return false;

    }

    /**
     * 生成随机验证码
     * @param digits
     * @return
     */
    public static String getRandCode(int digits) {
        StringBuilder sBuilder = new StringBuilder();
        Random rd = new Random((new Date()).getTime());

        for(int i = 0; i < digits; ++i) {
            sBuilder.append(String.valueOf(rd.nextInt(9)));
        }

        return sBuilder.toString();
    }
}
