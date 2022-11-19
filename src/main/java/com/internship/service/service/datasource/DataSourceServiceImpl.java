package com.internship.service.service.datasource;

import com.internship.service.annotations.ChangeDatabase;
import com.internship.service.entity.DataSourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Service
public class DataSourceServiceImpl implements DataSourceService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    @Lazy
    public DataSourceServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void addDataSource(DataSourceEntity dataSourceEntity) {
        System.out.println(dataSourceEntity.getPassword());
        jdbcTemplate.update("call add_datasource(?, ?, ?, ?)", dataSourceEntity.getName(), dataSourceEntity.getUsername(),
                dataSourceEntity.getPassword(), dataSourceEntity.getJdbcUrl());
    }

    @Override
    public void removeDataSource(String name) {
        jdbcTemplate.update("DELETE FROM databases WHERE name=?", name);
    }

    @Override
    public String getCurrentDb() {
        return jdbcTemplate.queryForObject("SELECT current_database()", String.class);
    }

}
