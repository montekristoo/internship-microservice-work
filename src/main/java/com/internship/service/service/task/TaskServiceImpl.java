package com.internship.service.service;

import com.internship.service.dbConfig.RouterDataSource;
import com.internship.service.entity.TaskEntity;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@EnableScheduling
@Service
public class TaskServiceImpl implements TaskService{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String SQL = "INSERT INTO test_table(description) VALUES ('test_description')";


    @Scheduled(fixedDelay = 2000)
    public void insertIntoDb1() {
        RouterDataSource.setContext("db_1");
        jdbcTemplate.execute(SQL);
    }
    @Scheduled(fixedDelay = 2000)
    public void insertIntoDb2() {
        RouterDataSource.setContext("db_2");
        System.out.println(RouterDataSource.getCurrentSource());
        jdbcTemplate.execute(SQL);
    }
    @Scheduled(fixedDelay = 2000)
    public void insertIntoDb3() {
        RouterDataSource.setContext("db_3");
        jdbcTemplate.execute(SQL);
    }

    @Scheduled(fixedDelay = 2000)
    public void insertIntoDb4() {
        RouterDataSource.setContext("db_4");
        System.out.println(RouterDataSource.getCurrentSource());
        jdbcTemplate.execute(SQL);
    }

    @Override
    public List<TaskEntity> getAll() {
        return jdbcTemplate.query("SELECT * FROM test_table;", new BeanPropertyRowMapper<>(TaskEntity.class));
    }

    @Override
    public void truncateTable() {
        jdbcTemplate.execute("TRUNCATE TABLE test_table;");
    }

    @Override
    public String getCurrentDb() {
        return jdbcTemplate.queryForObject("SELECT current_database()", String.class);
    }
}
