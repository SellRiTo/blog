package com.blog.blog.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @program: blog
 * @description: redisson配置
 * @author: txr
 * @create: 2020-03-08 21:50
 */

@Configuration
public class RedissonManager {

   /* @Value("${spring.redis.cluster.nodes}")
   private String cluster;


    @Value("${spring.redis.password}")
    private String  password;

    @Bean
    public RedissonClient getRession() throws IOException {
        String[] nodes = cluster.split(",");
        for (int i=0; i<nodes.length;i++){
            nodes[i]  = "redis://"+nodes[i];
        }
        RedissonClient redissonClient = null;
        Config config =new Config();
        ClusterServersConfig clusterServersConfig = config.useClusterServers()
                .setScanInterval(2000)
                .addNodeAddress(nodes);
         if (!StringUtils.isEmpty(password)){
             clusterServersConfig.setPassword(password);
         }
        redissonClient = Redisson.create(config);
        System.out.println("redis集群配置"+redissonClient.getConfig().toJSON().toString());
        return redissonClient;
    }*/


}
