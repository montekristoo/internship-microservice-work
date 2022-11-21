package com.internship.service.service.rootdb;

import com.internship.service.annotations.SetDatabase;
import com.internship.service.entity.DataSourceEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RootDatabaseServiceImpl implements RootDatabaseService {
    @Autowired
    @Lazy
    private JdbcTemplate jdbcTemplate;

    @Override
    @SetDatabase(value = "main_db")
    public void addDataSource(DataSourceEntity dataSourceEntity) {
        jdbcTemplate.update("call add_datasource(?, ?, ?, ?, ?)", dataSourceEntity.getName(), dataSourceEntity.getUsername(),
                dataSourceEntity.getPassword(), dataSourceEntity.getJdbcUrl(), dataSourceEntity.getDriverClassName());
    }

    @Override
    @SetDatabase(value = "main_db")
    public void removeDataSource(String name) {
        jdbcTemplate.update("DELETE FROM databases WHERE name=?", name);
    }
}
