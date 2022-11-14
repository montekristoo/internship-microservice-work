package com.internship.service.service;

import com.internship.service.entity.DbEntity;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Repository
public interface DataSourceService {
    Set<DbEntity> findAll() throws SQLException;
}
