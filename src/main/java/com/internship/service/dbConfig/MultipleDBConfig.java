package com.internship.service.dbConfig;

import com.internship.service.entity.DbEntity;
import com.internship.service.service.DataSourceService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
    public DataSource getDataSource() throws SQLException {
        final Map<Object, Object> dataSources = this.createDataSources();
        final RouterDataSource routerDataSource = new RouterDataSource();
        routerDataSource.setTargetDataSources(dataSources);
        routerDataSource.setDefaultTargetDataSource(dataSources.get("db_1"));
        return routerDataSource;
    }

    public Set<DbEntity> getDbsInfo() throws SQLException {
        return dataSourceService.findAll();
    }

    public Map<Object, Object> createDataSources() throws SQLException {
        final Map<Object, Object> result = new HashMap<>();
        getDbsInfo().forEach((db) ->
                result.put(db.getName(), createDataSource(db)));
        return result;
    }

    public DataSource createDataSource(DbEntity dbEntity) {
        return DataSourceBuilder.create()
                .username(dbEntity.getUsername())
                .password(dbEntity.getPassword())
                .username(dbEntity.getUsername())
                .url(dbEntity.getJdbcUrl())
                .build();
    }

}
