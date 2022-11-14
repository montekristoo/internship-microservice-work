package com.internship.service.service;

import com.internship.service.dbConfig.RouterDataSource;
import com.internship.service.entity.DbEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataSourceServiceImpl implements DataSourceService {

    private static final String DB_URL = "jdbc:postgresql://localhost:3002/main_db";
    private static final String USERNAME = "postgres";
    private static final String SQL_GET_DATA = "SELECT * FROM databases;";
    private static final String MAIN_PASSWORD = "internship";
    private static final String OTHER_DB_PASSWORD = "internship";


    @Override
    public Set<DbEntity> findAll() throws SQLException {

        Set<DbEntity> dbEntityList = new HashSet<>();
        ResultSet resultSet = getResultsFromConnectionQuery();
        while (resultSet.next()) {
            DbEntity dbEntity = new DbEntity();
            dbEntity.setName(resultSet.getString("name"));
            System.out.println(dbEntity.getName());
            dbEntity.setUsername(resultSet.getString("username"));
            dbEntity.setPassword(OTHER_DB_PASSWORD);
            resultSet.getString("password");
            dbEntity.setJdbcUrl(resultSet.getString("jdbc_url"));
            dbEntityList.add(dbEntity);
        }
        return dbEntityList;
    }

    private ResultSet getResultsFromConnectionQuery() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USERNAME, MAIN_PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_DATA);
        return preparedStatement.executeQuery();
    }

//    public List<String> getExistingDatabases() {
//        if (dataSource == null) {
//
//            return new ArrayList<>();
//        }
//        else {
//
//        }
//    }

}
