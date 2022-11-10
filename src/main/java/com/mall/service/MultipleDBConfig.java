package com.mall.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class MultipleDBConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "main.datasource")
    public DataSource mainDbConfig() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "second.datasource")
    public DataSource secondDbConfig() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "third.datasource")
    public DataSource thirdDbConfig() {
        return DataSourceBuilder.create().build();
    }

}
