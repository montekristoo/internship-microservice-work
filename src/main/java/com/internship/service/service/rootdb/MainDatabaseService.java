package com.internship.service.service.rootdb;

import com.internship.service.entity.DataSourceEntity;

import java.util.List;

public interface MainDatabaseService {
    void addDataSource(DataSourceEntity dataSourceEntity);

    void removeDataSource(String name);

    List<DataSourceEntity> findAll();

    DataSourceEntity getCachedDb(String name);

    DataSourceEntity findByName(String name);

    String getCurrentDb();
}
