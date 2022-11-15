package com.internship.service.service.datasource;

import com.internship.service.entity.DataSourceEntity;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.Set;

@Repository
public interface DataSourceService {
    Set<DataSourceEntity> findAll() throws SQLException;
    void addDataSource(DataSourceEntity dataSourceEntity);
    void removeDataSource(String name);
}
