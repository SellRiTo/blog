package com.blog.blog.module.rabbitmq;

import com.blog.blog.config.RabbitMQ.RabbitMQConfig;
import com.blog.blog.config.RabbitMQ.TopicRabbitMQConfig;
import com.blog.blog.module.sysuser.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: blog
 * @description: 消费者
 * @author: txr
 * @create: 2020-05-28 00:14
 */
@RestController
@RequestMapping("/rabbit")
@Slf4j
public class Produc {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void  send(SysUser sysUser){

        String context = "hello "+sdf.format(new Date());
        log.info("sned :{}",context);
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME,sysUser);
    }

    @GetMapping("/fanoutSend")
    public void FanoutSend() {
        String context = "hi, fanout msg , 时间：{} "+sdf.format(new Date());
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("fanout_exchange","", context);
    }

    @GetMapping("/TopicSendOne")
    public void topicSendOne() {
        String context = "Hi, Topic msg  One, 时间: "+sdf.format(new Date()) ;
        log.info("{}",context);
        SysUser sysUser = new SysUser();
        sysUser.setAccount("topic_one");
        sysUser.setName("测试");
        this.rabbitTemplate.convertAndSend(TopicRabbitMQConfig.EXCHANGE_TOPIC,"topic.one", sysUser);
    }

    @GetMapping("/TopicSendTwo")
    public void topicSendTwo() {
        String context = "Hi, Topic msg  Two, 时间: "+sdf.format(new Date());
        log.info("{}",context);
        SysUser sysUser = new SysUser();
        sysUser.setAccount("topic_Two");
        sysUser.setName("测试");
        this.rabbitTemplate.convertAndSend(TopicRabbitMQConfig.EXCHANGE_TOPIC,"topic.two",sysUser);
    }
}
