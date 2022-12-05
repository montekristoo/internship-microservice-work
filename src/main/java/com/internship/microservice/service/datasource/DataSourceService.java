package com.internship.microservice.service.datasource;

import com.internship.microservice.entity.DataSourceEntity;

import java.util.List;

public interface DataSourceService {
    void addDataSource(DataSourceEntity dataSrc);
    void removeDataSource(String name);
    List<DataSourceEntity> findAll();
    DataSourceEntity findByName(String name);
    String getCurrentDatabase();
    void updateDatabase(DataSourceEntity dataSrc, Long id);
}
