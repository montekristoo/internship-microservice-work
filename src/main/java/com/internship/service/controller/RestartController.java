package com.internship.service.controller;

import com.internship.service.ServiceApplication;
import com.internship.service.dbConfig.RouterDataSource;
import com.internship.service.service.RestartService;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

@RestController
@RequestMapping("/")
public class RestartController {

    @Autowired
    private RestartService restartService;
    @Autowired
    private DataSource dataSource;

    @GetMapping("restart")
    public void restart() {
        ((RouterDataSource) dataSource).getResolvedDataSources().forEach((k, v) -> {
            try {
                v.unwrap(HikariDataSource.class).close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        restartService.restartApp();
        //ServiceApplication.restart();
    }
}
