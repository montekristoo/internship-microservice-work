package com.internship.service.service;

import com.internship.service.entity.DbEntity;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface DataSourceService {
    DbEntity findByName(String name);
    List<DbEntity> findAll() throws SQLException;
}
