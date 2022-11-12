package com.internship.service.dbConfig;

import com.internship.service.entity.DbEntity;
import com.internship.service.service.DataSourceService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class MultipleDBConfig {

    @Lazy
    @Autowired
    private DataSourceService dataSourceService;

    @Bean
    @Primary
    public DataSource getDataSource() throws SQLException {
        final Map<Object, Object> dataSources = this.createDataSources();
        final RouterDataSource routerDataSource = new RouterDataSource();
        routerDataSource.setTargetDataSources(dataSources);
        routerDataSource.setDefaultTargetDataSource(dataSources.get("db_2"));
        return routerDataSource;
    }

    public List<DbEntity> getDbsInfo() throws SQLException {
        return dataSourceService.findAll();
    }

    public Map<Object, Object> createDataSources() throws SQLException {
        final Map<Object, Object> result = new HashMap<>();
        getDbsInfo().forEach((db) -> {
            result.put(db.getName(), createDataSource(db));
        });
        return result;
    }

    public DataSource createDataSource(DbEntity dbEntity) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbEntity.getJdbcUrl());
        config.setUsername(dbEntity.getUsername());
        config.setPassword(dbEntity.getPassword());
        return new HikariDataSource(config);
    }

}
