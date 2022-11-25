package com.internship.service.service.rootdb;

import com.internship.service.entity.DataSourceEntity;

import java.sql.SQLException;
import java.util.List;

public interface MainDatabaseService {
    void addDataSource(DataSourceEntity dataSourceEntity);

    void removeDataSource(String name);

    List<DataSourceEntity> findAll();
    DataSourceEntity findByName(String name) throws SQLException;
    String getCurrentDb();
}
