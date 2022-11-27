package com.internship.microservice.service.datasource;

import com.internship.microservice.entity.DataSourceEntity;
import com.internship.microservice.entity.TaskEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DataSourceService {
    void addDataSource(DataSourceEntity dataSrc);
    void removeDataSource(String name);
    List<DataSourceEntity> findAll();
    DataSourceEntity findByName(String name) throws SQLException;
    String getCurrentDatabase();
    void updateDatabase(DataSourceEntity dataSrc, Long id);
    void addTestData(TaskEntity task);
    Map<String, String> getPasswordAndSalt(Long id);
}
