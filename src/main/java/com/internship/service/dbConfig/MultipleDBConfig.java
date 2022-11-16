package com.internship.service.dbConfig;

import com.internship.service.entity.DataSourceEntity;
import com.internship.service.service.datasource.DataSourceService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
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
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public List<DataSourceEntity> getDbsInfo() {
        return dataSourceService.findAll();
    }

    public Map<Object, Object> createDataSources() {
        final Map<Object, Object> result = new HashMap<>();
        getDbsInfo().forEach((db) ->
                result.put(db.getName(), createDataSource(db)));
        return result;
    }

    public DataSource createDataSource(DataSourceEntity dataSourceEntity) {
        HikariConfig config = new HikariConfig();
        config.setUsername(dataSourceEntity.getUsername());
        config.setPassword(dataSourceEntity.getPassword());
        config.setJdbcUrl(dataSourceEntity.getJdbcUrl());
        return new HikariDataSource(config);
    }

}
