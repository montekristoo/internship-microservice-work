package com.internship.service.config;

import com.internship.service.entity.DataSourceEntity;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
@Slf4j
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
    public RoutingDataSource getDataSource() {
        final Map<Object, Object> dataSources = this.createDataSource();
        final RoutingDataSource routerDataSource = new RoutingDataSource();
        routerDataSource.setTargetDataSources(dataSources);
        routerDataSource.setDefaultTargetDataSource(dataSources.get("main_db"));
        routerDataSource.afterPropertiesSet();
        log.info("In bean: " + routerDataSource.getResolvedDefaultDataSource());
        return routerDataSource;
    }


    public Map<Object, Object> createDataSource() {
        final Map<Object, Object> result = new HashMap<>();
        result.put(MAIN_DB, createDefaultDataSource());
        return result;
    }

    public DataSource createDefaultDataSource() {
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
