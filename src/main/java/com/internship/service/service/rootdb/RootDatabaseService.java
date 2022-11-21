package com.internship.service.service.rootdb;

import com.internship.service.entity.DataSourceEntity;

public interface RootDatabaseService {
    void addDataSource(DataSourceEntity dataSourceEntity);
    void removeDataSource(String name);
}
