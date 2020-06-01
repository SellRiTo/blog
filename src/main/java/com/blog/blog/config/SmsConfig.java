package com.blog.blog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @program: blog
 * @description: 短信配置类
 * @author: txr
 * @create: 2020-03-12 10:11
 */

@Data
@Configuration
@ConfigurationProperties("sms.api")
public class SmsConfig {

    private String accessKeyId;
    private String accessSecret;
    private String signName;
    private String templateCode;

}
