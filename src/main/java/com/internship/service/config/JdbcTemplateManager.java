package com.internship.service.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

@Component
@Slf4j
public class JdbcTemplateManager {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dsToChange) {
        jdbcTemplate.setDataSource(dsToChange);
    }

    public void removeCurrentDataSource() throws SQLException {
        log.info("Removing...");
        jdbcTemplate.getDataSource().unwrap(HikariDataSource.class).close();
        log.info("Current data: " + jdbcTemplate.getDataSource());
    }

}
