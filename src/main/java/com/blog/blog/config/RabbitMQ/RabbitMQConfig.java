package com.blog.blog.config.RabbitMQ;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.Topic;

/**
 * @program: blog
 * @description: RabbitMQ配置信息
 * @author: txr
 * @create: 2020-05-27 09:56
 */

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "txr_blog_queue";
    public static final String EXCHANGE_NAME = "txr_blog_exchange";
    public static final String ROUTE_KEY = "txr_blog_route_key";

    @Bean
    public Queue queue(){
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTE_KEY);
    }


}
