package com.internship.service.service.rootdb;

import com.internship.service.entity.DataSourceEntity;

import java.util.List;

public interface RootDatabaseService {
    List<DataSourceEntity> findAll();
    void addDataSource(DataSourceEntity dataSourceEntity);
    void removeDataSource(String name);
}
