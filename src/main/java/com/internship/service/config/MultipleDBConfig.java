package com.internship.service.config;

import com.internship.service.entity.DataSourceEntity;
import com.internship.service.service.datasource.DataSourceService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Lazy
public class MultipleDBConfig {

    private final DataSourceService dataSourceService;

    @Autowired
    public MultipleDBConfig(DataSourceService dataSourceService) {
        this.dataSourceService = dataSourceService;
    }

    @Bean
    @Primary
    public RouterDataSource getDataSource() {
        final Map<Object, Object> dataSources = this.createDataSources();
        final RouterDataSource routerDataSource = new RouterDataSource();
        routerDataSource.setTargetDataSources(dataSources);
        routerDataSource.setDefaultTargetDataSource(dataSources.get("main_db"));
        routerDataSource.afterPropertiesSet();
        System.out.println("In bean: " + routerDataSource.getResolvedDefaultDataSource());
        return routerDataSource;
    }

    @Bean
    @Scope("prototype")
    public List<DataSourceEntity> getDbsInfo() {
        return
    }


    public Map<Object, Object> createDataSources() {
        final Map<Object, Object> result = new HashMap<>();
        getDbsInfo().forEach((db) -> {
            if (db.getName().equals("main_db")) {
            result.put(db.getName(), createDataSource(db));
            }
        });
        return result;
    }


    public DataSource createDataSource(DataSourceEntity dataSourceEntity) {
        HikariConfig config = new HikariConfig();
        config.setUsername("postgres");
        config.setPassword("internship");
        config.setJdbcUrl("jdbc:postgresql://localhost:3002/main_db");
        return new HikariDataSource(config);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
