package com.internship.service.service.rootdb;

import com.internship.service.entity.DataSourceEntity;

import java.util.List;

public interface RootDatabaseService {
    void addDataSource(DataSourceEntity dataSourceEntity);
    void removeDataSource(String name);
    List<DataSourceEntity> findAll();
    List<DataSourceEntity> getCachedDb();
    DataSourceEntity findByName(String name);
}