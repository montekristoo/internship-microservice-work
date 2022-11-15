package com.internship.service.service.restart;

import com.internship.service.dbConfig.RouterDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.SQLException;

@Service
public class RestartService {
    @Autowired
    private RestartEndpoint restartEndpoint;
    @Autowired
    private DataSource dataSource;

    public void restartApp() {
        ((RouterDataSource) dataSource).getResolvedDataSources().forEach((k, v) -> {
            try {
                v.unwrap(HikariDataSource.class).close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        restartEndpoint.restart();
    }
}
