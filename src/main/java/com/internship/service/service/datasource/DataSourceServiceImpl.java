package com.internship.service.service.datasource;

import com.internship.service.entity.DataSourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Service
public class DataSourceServiceImpl implements DataSourceService {
    private final JdbcTemplate jdbcTemplate;
    private static final String DB_URL = "jdbc:postgresql://localhost:3002/main_db";
    private static final String USERNAME = "postgres";
    private static final String SQL_GET_DATA = "SELECT * FROM databases;";
    private static final String MAIN_PASSWORD = "internship";

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
                    DataSourceEntity dataSourceEntity = DataSourceEntity.builder()
                            .name(resultSet.getString("name"))
                            .username(resultSet.getString("username"))
                            .password(MAIN_PASSWORD)
                            .jdbcUrl(resultSet.getString("jdbc_url"))
                            .build();
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

    @Override
    public String getCurrentDb() {
        return jdbcTemplate.queryForObject("SELECT current_database()", String.class);
    }

    public void testData() {
    }
}
