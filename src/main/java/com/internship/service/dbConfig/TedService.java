package com.internship.service.dbConfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@EnableScheduling
@Service
public class TedService {

    private RouterDataSource routerDataSource = new RouterDataSource();
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Map<String, DataSource> dataSource;
    private String sql = "INSERT INTO users (first_name) VALUES ('test_user');";
    @Scheduled(fixedDelay = 2000)
    public void insertIntoDb1() {
        RouterDataSource.setContext("db_1");
        System.out.println(RouterDataSource.getCurrentSource());
        jdbcTemplate.execute(sql);
    }

    @Scheduled(fixedDelay = 100)
    public void insertIntoDb2() {
        RouterDataSource.setContext("db_2");
        System.out.println(RouterDataSource.getCurrentSource());
        jdbcTemplate.execute(sql);
    }

    @Scheduled(fixedDelay = 3000)
    public void insertIntoDb3() {
        RouterDataSource.setContext("db_3");
        System.out.println(RouterDataSource.getCurrentSource());
        jdbcTemplate.execute(sql);
    }

}
