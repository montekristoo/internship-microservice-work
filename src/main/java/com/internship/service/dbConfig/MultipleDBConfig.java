package com.internship.service.dbConfig;

import com.internship.service.annotations.DataSourceAspect;
import com.internship.service.entity.DataSourceEntity;
import com.internship.service.service.datasource.DataSourceService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
public class MultipleDBConfig {

    private final DataSourceService dataSourceService;

    public MultipleDBConfig(DataSourceService dataSourceService) {
        this.dataSourceService = dataSourceService;
    }

    @Bean
    @Primary
    public RouterDataSource getDataSource() throws SQLException {
        final Map<Object, Object> dataSources = this.createDataSources();
        final RouterDataSource routerDataSource = new RouterDataSource();
        routerDataSource.setTargetDataSources(dataSources);
        routerDataSource.setDefaultTargetDataSource(dataSources.get("main_db"));
        routerDataSource.afterPropertiesSet();
        return routerDataSource;
    }

    public Set<DataSourceEntity> getDbsInfo() throws SQLException {
        return dataSourceService.findAll();
    }

    public Map<Object, Object> createDataSources() throws SQLException {
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
        System.out.println(config);
        return new HikariDataSource(config);
    }

}
