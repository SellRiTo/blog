package com.blog.blog.config.RabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: blog
 * @description: 广播RabbitMQ
 * @author: txr
 * @create: 2020-05-28 11:33
 */

@Configuration
public class FanoutRabbitMQConfig {

    public static final String FANOUT_A = "fanout_a";
    public static final String FANOUT_B = "fanout_b";
    public static final String FANOUT_C = "fanout_c";
    public static final String FANOUT_EXCHANGE = "fanout_exchange";


    @Bean
    public Queue FanoutQueueA(){
        return  new Queue(FANOUT_A);
    }

    @Bean
    public Queue FanoutQueueB(){
        return  new Queue(FANOUT_B);
    }

    @Bean
    public Queue FanoutQueueC(){
        return  new Queue(FANOUT_C);
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding bindingQueueA(Queue FanoutQueueA,FanoutExchange  fanoutExchange){
        return BindingBuilder.bind(FanoutQueueA).to(fanoutExchange);
    }

    @Bean
    public Binding bindingQueueB(Queue FanoutQueueB,FanoutExchange  fanoutExchange){
        return BindingBuilder.bind(FanoutQueueB).to(fanoutExchange);
    }
    @Bean
    public Binding bindingQueueC(Queue FanoutQueueC,FanoutExchange  fanoutExchange){
        return BindingBuilder.bind(FanoutQueueC).to(fanoutExchange);
    }
}
