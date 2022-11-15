package com.internship.service.service.datasource;

import com.internship.service.entity.DataSourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.lang.instrument.UnmodifiableClassException;
import java.sql.*;
import java.util.*;

@Service
@RefreshScope
public class DataSourceServiceImpl implements DataSourceService {
    private final JdbcTemplate jdbcTemplate;
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/main_db";
    private static final String USERNAME = "postgres";
    private static final String SQL_GET_DATA = "SELECT * FROM databases;";
    private static final String MAIN_PASSWORD = "internship";
    private static final String OTHER_DB_PASSWORD = "internship";

    @Autowired
    @Lazy
    public DataSourceServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<DataSourceEntity> findAll() {
        List<DataSourceEntity> dataSourceEntityList = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            resultSet = getResultsFromConnectionQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    DataSourceEntity dataSourceEntity = new DataSourceEntity();
                    dataSourceEntity.setName(resultSet.getString("name"));
                    System.out.println(dataSourceEntity.getName());
                    dataSourceEntity.setUsername(resultSet.getString("username"));
                    dataSourceEntity.setPassword(OTHER_DB_PASSWORD);
                    resultSet.getString("password");
                    dataSourceEntity.setJdbcUrl(resultSet.getString("jdbc_url"));
                    dataSourceEntityList.add(dataSourceEntity);
                }
                resultSet.close();
            }
            return dataSourceEntityList;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ResultSet getResultsFromConnectionQuery() {
        try(Connection connection = DriverManager.getConnection(DB_URL, USERNAME, MAIN_PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_DATA);
            return preparedStatement.executeQuery();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addDataSource(DataSourceEntity dataSourceEntity) {
        System.out.println(dataSourceEntity.getPassword());
        jdbcTemplate.update("call add_datasource(?, ?, ?, ?)", dataSourceEntity.getName(), dataSourceEntity.getUsername(),
                                                                    dataSourceEntity.getPassword(), dataSourceEntity.getJdbcUrl());
    }

    @Override
    public void removeDataSource(String name) {
        jdbcTemplate.update("DELETE FROM databases WHERE name=?", name);
    }
}
