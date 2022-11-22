package com.internship.service.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;


@Configuration
public class MultipleDBConfig {

    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    private static final String MAIN_DB = "main_db";

    @Bean
    @Primary
    public DataSource defaultDataSource() {
        HikariConfig config = new HikariConfig();
        config.setUsername(username);
        config.setPassword(password);
        config.setJdbcUrl(jdbcUrl);
        config.setDriverClassName(driverClassName);
        return new HikariDataSource(config);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
