package com.internship.microservice.config;

import com.internship.microservice.routing.RoutingDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


@Configuration
public class MainDatabaseConfig {

    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.hikari.driver-class-name}")
    private String driverClassName;
    private final static String DEFAULT = "main_db";

    @Bean
    @Primary
    public RoutingDataSource abstractRoutingDataSource()  {
        final RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(defaultDataSource());
        routingDataSource.addDataSource(DEFAULT, defaultDataSource());
        routingDataSource.setLenientFallback(false);
        return routingDataSource;
    }

    public DataSource defaultDataSource() {
        HikariConfig config = new HikariConfig();
        config.setUsername(username);
        config.setPassword(password);
        config.setJdbcUrl(jdbcUrl);
        config.setDriverClassName(driverClassName);
        config.setPoolName(DEFAULT);
        return new HikariDataSource(config);
    }

}
