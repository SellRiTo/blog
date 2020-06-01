package com.blog.blog;

import com.blog.blog.module.rabbitmq.Comsume;
import com.blog.blog.module.rabbitmq.Produc;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: blog
 * @description: rabbitMQ测试类
 * @author: txr
 * @create: 2020-05-28 11:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQTest {

    @Autowired
    private Produc product;

    @Test
    public void process(){
        product.topicSendOne();
        product.topicSendTwo();
    }
}
