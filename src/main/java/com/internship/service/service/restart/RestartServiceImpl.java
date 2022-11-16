package com.internship.service.service.restart;

import com.internship.service.dbConfig.RouterDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.restart.RestartEndpoint;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;

@Service
public class RestartServiceImpl implements RestartService {
    @Autowired
    private RestartEndpoint restartEndpoint;
    @Autowired
    private DataSource dataSource;

    public RestartServiceImpl() {}

    @Override
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
