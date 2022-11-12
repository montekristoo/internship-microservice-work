package com.internship.service.service;

import com.internship.service.entity.DbModel;
import com.internship.service.mapper.DbMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataSourceServiceImpl implements DataSourceService {

    @Override
    public DbModel findByName(String name) {
//        String sql = "SELECT * FROM databases WHERE name = ?";
//        return jdbcTemplate.queryForObject(sql, new DbMapper(), new Object[]{name});
        return null;
    }

    @Override
    public List<DbModel> findAll() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/settings";
        String username = "postgres";
        String password = "1s2a3dqwer5";
        String sql = "SELECT * FROM databases;";
        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<DbModel> dbModelList = new ArrayList<>();
        while (resultSet.next()) {
            DbModel dbModel = new DbModel();
            dbModel.setName(resultSet.getString("name"));
            dbModel.setUsername(resultSet.getString("username"));
            dbModel.setPassword(resultSet.getString("password"));
            dbModel.setJdbcUrl(resultSet.getString("jdbc_url"));
            dbModelList.add(dbModel);
        }
        return dbModelList;
    }


}
