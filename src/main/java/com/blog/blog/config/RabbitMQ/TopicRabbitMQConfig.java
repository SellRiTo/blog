package com.blog.blog.config.RabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.PublicKey;

/**
 * @program: blog
 * @description: 主题消息
 * @author: txr
 * @create: 2020-05-28 14:11
 */
@Configuration
public class TopicRabbitMQConfig {

    public static final String QUEUE_TOPICA="topic_one";
    public static final String QUEUE_TOPICB="topic_two";
    public static final String EXCHANGE_TOPIC="topic_exchange";


    @Bean
    public Queue queueOne(){
        return new Queue(QUEUE_TOPICA);
    }

    @Bean
    public Queue queueTwo(){
        return new Queue(QUEUE_TOPICB);
    }


    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(EXCHANGE_TOPIC);
    }

    @Bean
    public Binding bindingTopicA(Queue queueOne, TopicExchange topicExchange){
        return BindingBuilder.bind(queueOne).to(topicExchange).with("topic.one");
    }

    @Bean
    public Binding bindingTopicB(Queue queueTwo, TopicExchange topicExchange){
        return BindingBuilder.bind(queueTwo).to(topicExchange).with("topic.#");
    }

}
