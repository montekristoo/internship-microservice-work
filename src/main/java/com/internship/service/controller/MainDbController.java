package com.internship.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.Map;

@RestController
@RequestMapping("/")
public class MainDbController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Map<String, DataSource> sourceMap;

    @GetMapping("databases")
    public void getAllDb() {
        sourceMap.forEach((k, v) -> {
            System.out.println(v);
            jdbcTemplate.setDataSource(v);
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS second_test (id int);");
        });
    }

}
