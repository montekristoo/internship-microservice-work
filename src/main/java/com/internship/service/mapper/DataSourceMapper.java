package com.internship.service.mapper;

import com.internship.service.entity.DbModel;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

public class DataSourceMapper {
    public static DataSource modelToDataSource(DbModel dbModel) {
        return DataSourceBuilder
                .create()
                .username(dbModel.getUsername())
                .password(dbModel.getPassword())
                .url(dbModel.getJdbcUrl())
                .build();
    }
}
