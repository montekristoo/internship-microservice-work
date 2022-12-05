package com.internship.microservice.service.datasource;

import com.internship.microservice.entity.DataSourceEntity;
import com.internship.microservice.exception.DatabaseAlreadyExists;
import com.internship.microservice.exception.DatabaseNotFoundException;
import com.internship.microservice.mapper.DataSourceMapper;
import com.internship.microservice.util.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.List;

@Slf4j
@Service
@EnableScheduling
public class DataSourceServiceImpl implements DataSourceService {
    private final DataSourceMapper dbMapper;

    @Autowired
    public DataSourceServiceImpl(DataSourceMapper dbMapper) {
        this.dbMapper = dbMapper;
    }

    @Override
    public void addDataSource(DataSourceEntity dataSrc) {
        if (dbMapper.findByName(dataSrc.getName()) != null) {
            throw new DatabaseAlreadyExists(dataSrc.getName());
        }
        PasswordUtils.writeToFile(dataSrc.getName(), dataSrc.getPassword());
        byte[] salt = PasswordUtils.generateSalt();
        String password = PasswordUtils.generateHashingPassword(dataSrc.getPassword(), salt);
        dataSrc.setSalt(Base64Utils.encodeToString(salt));
        dataSrc.setPassword(password);
        dbMapper.addDatabase(dataSrc);
    }

    @Override
    public void removeDataSource(String name) {
        DataSourceEntity dataSourceEntity = findByName(name);
        if (dataSourceEntity == null) {
            throw new DatabaseNotFoundException(name);
        }
        dbMapper.removeDatabase(name);
    }

    @Override
    public void updateDatabase(DataSourceEntity dataSrc, Long id) {
        dbMapper.updateDatabase(dataSrc, id);
    }

    @Override
    public List<DataSourceEntity> findAll() {
        return dbMapper.findAll();
    }

    @Override
    public DataSourceEntity findByName(String name) {
        DataSourceEntity dataSrc = dbMapper.findByName(name);
        if (dataSrc == null) {
            throw new DatabaseNotFoundException(name);
        }
        return dataSrc;
    }

    @Override
    public String getCurrentDatabase() {
        return dbMapper.getCurrentDatabase();
    }

}
