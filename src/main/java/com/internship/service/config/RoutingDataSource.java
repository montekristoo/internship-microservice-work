package com.internship.service.config;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {

    private static final Map<Object, DataSource> sources = new HashMap<>();
    @Override
    public Object determineCurrentLookupKey() {
        log.info(String.format("Current context: %s", DataSourceContext.getCurrentContext()));
        if (DataSourceContext.getCurrentContext() == null) {
            return null;
        }
        return DataSourceContext.getCurrentContext();
    }

    public void addDataSource(String name, DataSource source) {
        log.info("Name entry: " + name);
        sources.put(name, source);
        Map<Object, Object> dataSourcesToObjects = new HashMap<>(sources);
        setTargetDataSources(dataSourcesToObjects);
        afterPropertiesSet();
    }

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
    }

    @Override
    protected DataSource determineTargetDataSource() {
        return super.determineTargetDataSource();
    }

    public void closeDataSource() {
        HikariDataSource hikariDataSource = (HikariDataSource) determineTargetDataSource();
        sources.remove(hikariDataSource.getPoolName());
        hikariDataSource.close();
    }
}
