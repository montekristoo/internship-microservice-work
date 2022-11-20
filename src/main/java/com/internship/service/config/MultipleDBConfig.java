package com.internship.service.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultipleDBConfig {

    @Autowired
    @Lazy
    private JdbcTemplate jdbcTemplate;


    @Bean
    @Primary
    public RoutingDataSource getDataSource() {
        final Map<Object, Object> dataSources = this.createDataSources();
        final RoutingDataSource routerDataSource = new RoutingDataSource();
        routerDataSource.setTargetDataSources(dataSources);
        routerDataSource.setDefaultTargetDataSource(dataSources.get("main_db"));
        routerDataSource.setLenientFallback(true);
        routerDataSource.afterPropertiesSet();
        System.out.println("In bean: " + routerDataSource.getResolvedDefaultDataSource());
        return routerDataSource;
    }

//    @Bean
//    @Scope("prototype")
//    @Lazy
//    public List<DataSourceEntity> getDbsInfo() throws SQLException {
//        jdbcTemplate.setDataSource(createDefaultDataSource());
//
//        jdbcTemplate.getDataSource().unwrap(HikariDataSource.class).close();
//        return entityList;
//    }


    public Map<Object, Object> createDataSources() {
        final Map<Object, Object> result = new HashMap<>();
        result.put("main_db", createDefaultDataSource());
        return result;
    }

    public DataSource createDefaultDataSource() {
        HikariConfig config = new HikariConfig();
        config.setUsername("postgres");
        config.setPassword("internship");
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/main_db");
        return new HikariDataSource(config);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
