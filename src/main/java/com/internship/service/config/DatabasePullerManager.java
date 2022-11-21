package com.internship.service.config;

import com.internship.service.entity.DataSourceEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Slf4j
public class DatabasePullerManager {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AbstractRoutingDataSource routingDataSource;

    public List<DataSourceEntity> dataSourceEntities() {
        jdbcTemplate.setDataSource(routingDataSource.getResolvedDefaultDataSource());
        List<DataSourceEntity> dataSourceEntities = jdbcTemplate.query("SELECT * FROM databases", (rs, row_number) ->
                new DataSourceEntity(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("jdbc_url"),
                        rs.getString("driver_class_name")
                )
        );
        log.info(String.valueOf(dataSourceEntities.hashCode()));
        return dataSourceEntities;
    }

}
