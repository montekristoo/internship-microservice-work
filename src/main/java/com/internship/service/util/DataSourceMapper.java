package com.internship.service.util;

import com.internship.service.entity.DataSourceEntity;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DataSourceMapper {
    public static DataSource entityToDataSrc(DataSourceEntity dataSrcEntity) {
        HikariConfig config = new HikariConfig();
        config.setUsername(dataSrcEntity.getUsername());
        config.setPassword("internship");
        config.setJdbcUrl(dataSrcEntity.getJdbcUrl());
        return new HikariDataSource(config);
    }
}
