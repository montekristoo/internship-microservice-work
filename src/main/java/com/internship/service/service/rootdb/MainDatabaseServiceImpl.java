package com.internship.service.service.rootdb;

import com.internship.service.entity.DataSourceEntity;
import com.internship.service.mapper.MainDatabaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Primary
public class MainDatabaseServiceImpl implements MainDatabaseService {

    private final MainDatabaseMapper mapper;

    public MainDatabaseServiceImpl(MainDatabaseMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void addDataSource(DataSourceEntity dataSourceEntity) {
        mapper.addDatabase(dataSourceEntity);
    }

    @Override
    public void removeDataSource(String name) {
        mapper.removeDatabase(name);
    }

    @Override
    public List<DataSourceEntity> findAll() {
        return mapper.findAll();
    }

    @Override
    public DataSourceEntity findByName(String name) {
        return mapper.findByName(name);
    }

    @Override
    public String getCurrentDb() {
        return mapper.getCurrentDatabase();
    }
}
