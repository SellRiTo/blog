package com.blog.blog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @program: blog
 * @description:
 * @author: txr
 * @create: 2020-05-28 15:44
 */
@Configuration
@ConfigurationProperties("upload")
@Data
public class FileProperties {
    private String location;
}
