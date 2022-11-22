package com.internship.service.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

@Component
public class JdbcTemplateManager {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dsToChange) {
        jdbcTemplate.setDataSource(dsToChange);
    }

    public void removeCurrentDataSource() throws SQLException {
        jdbcTemplate.getDataSource().unwrap(HikariDataSource.class).close();
    }

    public DataSource getCurrentDataSource() {
        return jdbcTemplate.getDataSource();
    }

}
