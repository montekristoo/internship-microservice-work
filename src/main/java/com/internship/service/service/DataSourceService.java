package com.internship.service.service;

import com.internship.service.entity.DbModel;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface DataSourceService {
    DbModel findByName(String name);
    List<DbModel> findAll() throws SQLException;
}
