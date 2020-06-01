package com.blog.blog.config.druiddatasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: blog
 * @description:
 * @author: txr
 * @create: 2020-04-08 17:28
 */

@Configuration
public class DruidConfig {

    @Autowired
    private DruidProperties druidProperties;
    @Bean(name = "masterDataSource",initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DataSource masterDataSource(){
        DruidDataSource druidDataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(druidDataSource);
    }

    @Bean(name = "slaveDataSource",initMethod = "init")
    @ConfigurationProperties("spring.datasource.druid.slave")
    @ConditionalOnProperty(prefix = "spring.datasource.druid.slave",name = "enabled",havingValue = "true")
    public DataSource slaveDataSource(){
        DruidDataSource druidDataSource = DruidDataSourceBuilder.create().build();
        return druidProperties.dataSource(druidDataSource);
    }

    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dataSource(){
        Map<Object,Object> dataSources = new HashMap<>();
        dataSources.put(DataSourceType.MASTER.name(),masterDataSource());
        if (druidProperties.slaveEnable){
            dataSources.put(DataSourceType.SLAVE.name(),slaveDataSource());
        }
        return new DynamicDataSource(masterDataSource(),dataSources);
    }

}
