package com.blog.blog.module.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.blog.blog.config.RabbitMQ.FanoutRabbitMQConfig;
import com.blog.blog.config.RabbitMQ.TopicRabbitMQConfig;
import com.blog.blog.module.mail.service.impl.MailServiceImpl;
import com.blog.blog.module.sysuser.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * @program: blog
 * @description: 生产者
 * @author: txr
 * @create: 2020-05-28 00:13
 */
@Component
@Slf4j
public class Comsume {
    //注入发送邮件的各种实现方法
    @Autowired
    private MailServiceImpl mailService;
    //注入模板引擎
    @Autowired
    private TemplateEngine templateEngine;


    @RabbitListener(queues = "txr_blog_queue")
    public void  accpet(SysUser sysUser){
        log.info("消费了消息: {}",sysUser);
        try {

            ClassPathResource classPathResource = new ClassPathResource("/static/MailMessage.html");
            File file = classPathResource.getFile();
            FileChannel channel = new FileInputStream(file).getChannel();
            ByteBuffer  buffer = ByteBuffer.allocate((int) channel.size());
            channel.read(buffer);
            buffer.flip();
            String message = new String(buffer.array(), Charset.forName("utf-8"));
            mailService.sendHtmlMail(sysUser.getEmail(),"springboot 邮箱发送", message);

        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("邮件发送失败");
        }

    }

    @RabbitListener(queues = {FanoutRabbitMQConfig.FANOUT_A,FanoutRabbitMQConfig.FANOUT_B,FanoutRabbitMQConfig.FANOUT_C})
    public void  accpetFanout(String message){
        log.info("消费了广播消息: {}",message);
    }

    @RabbitListener(queues = TopicRabbitMQConfig.QUEUE_TOPICA)
    public void  accpetTopicOne(SysUser user){
        log.info("消费了主题消息: 队列是：One, 数据是： {}", JSON.toJSONString(user));
    }

    @RabbitListener(queues = TopicRabbitMQConfig.QUEUE_TOPICB)
    public void  accpetTopicTwo(SysUser user){
        log.info("消费了主题消息: 队列是：Two, 数据是： {}", JSON.toJSONString(user));
    }
}
