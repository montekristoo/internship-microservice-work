package com.internship.service.service;

import com.internship.service.entity.DbEntity;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataSourceServiceImpl implements DataSourceService {

    @Override
    public DbEntity findByName(String name) {
//        String sql = "SELECT * FROM databases WHERE name = ?";
//        return jdbcTemplate.queryForObject(sql, new DbMapper(), new Object[]{name});
        return null;
    }

    @Override
    public List<DbEntity> findAll() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/settings";
        String username = "postgres";
        String password = "1s2a3dqwer5";
        String sql = "SELECT * FROM databases;";
        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<DbEntity> dbEntityList = new ArrayList<>();
        while (resultSet.next()) {
            DbEntity dbEntity = new DbEntity();
            dbEntity.setName(resultSet.getString("name"));
            dbEntity.setUsername(resultSet.getString("username"));
            dbEntity.setPassword("1s2a3dqwer5");
            resultSet.getString("password");
            dbEntity.setJdbcUrl(resultSet.getString("jdbc_url"));
            dbEntityList.add(dbEntity);
        }
        return dbEntityList;
    }


}
