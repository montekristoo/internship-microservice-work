package com.internship.microservice.service.datasource;

import com.internship.microservice.entity.DataSourceEntity;

import java.sql.SQLException;
import java.util.List;

public interface DataSourceService {
    void addDataSource(DataSourceEntity dataSrc);
    void removeDataSource(String name);
    List<DataSourceEntity> findAll();
    DataSourceEntity findByName(String name) throws SQLException;
    String getCurrentDatabase();
    void updateDatabase(DataSourceEntity dataSrc, Long id);
    void createTableUsers();
    void dropTableUsers();
}
