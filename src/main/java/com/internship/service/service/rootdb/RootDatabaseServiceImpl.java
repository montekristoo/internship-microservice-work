package com.internship.service.service.rootdb;

import com.internship.service.annotations.ChangeDatabase;
import com.internship.service.entity.DataSourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RootDatabaseServiceImpl implements RootDatabaseService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    @Lazy
    public RootDatabaseServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @ChangeDatabase(value = "main_db")
    public List<DataSourceEntity> findAll() {
        List<DataSourceEntity> entityList = jdbcTemplate.query("SELECT * FROM databases", (rs, row_number) ->
                new DataSourceEntity(
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("jdbc_url")
                )
        );
        return entityList;
    }

    @Override
    @ChangeDatabase(value = "main_db")
    public void addDataSource(DataSourceEntity dataSourceEntity) {
        System.out.println(dataSourceEntity.getPassword());
        jdbcTemplate.update("call add_datasource(?, ?, ?, ?)", dataSourceEntity.getName(), dataSourceEntity.getUsername(),
                dataSourceEntity.getPassword(), dataSourceEntity.getJdbcUrl());
    }

    @Override
    @ChangeDatabase(value = "main_db")
    public void removeDataSource(String name) {
        jdbcTemplate.update("DELETE FROM databases WHERE name=?", name);
    }

}
