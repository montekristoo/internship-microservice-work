package com.internship.microservice.util;

import com.internship.microservice.entity.DataSourceEntity;
import com.internship.microservice.exception.WrongDatabaseCredentialsException;
import com.internship.microservice.routing.DataSourceContext;
import com.internship.microservice.service.datasource.DataSourceService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

@Component
public class DataSourceConverter {

    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private Map<String, String> clientPasswords;

    public Map<String, String> getPasswordAndSalt(Long id) {
        System.out.println(DataSourceContext.getCurrentContext());
        return dataSourceService.getPasswordAndSalt(id);
    }

    public DataSource entityToDataSource(DataSourceEntity dataSrcEntity) {
        String falsePasswordClient = clientPasswords.get(dataSrcEntity.getName());
        if (!PasswordUtils.verifyPassword(dataSrcEntity.getPassword(), dataSrcEntity.getSalt(), falsePasswordClient)) {
            throw new WrongDatabaseCredentialsException(dataSrcEntity.getName());
        }
        HikariConfig config = new HikariConfig();
        config.setUsername(dataSrcEntity.getUsername());
        config.setPassword(falsePasswordClient);
        config.setJdbcUrl(dataSrcEntity.getJdbcUrl());
        config.setDriverClassName(dataSrcEntity.getDriverClassName());
        config.setPoolName(dataSrcEntity.getName());
        return new HikariDataSource(config);
    }
}