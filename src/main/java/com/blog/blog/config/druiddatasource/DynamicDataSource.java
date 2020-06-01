package com.blog.blog.config.druiddatasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @program: blog
 * @description: 动态数据源
 * @author: txr
 * @create: 2020-04-08 17:45
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    public DynamicDataSource(DataSource defaultTargetSource, Map<Object,Object> targetDataSources){
        this.setDefaultTargetDataSource(defaultTargetSource);
        this.setTargetDataSources(targetDataSources);
        this.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
       return DynamicDataSourceContextHolder.getDataSourceType();
    }
}
