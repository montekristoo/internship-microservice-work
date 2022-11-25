package com.internship.service.controller;

import com.internship.service.entity.DataSourceEntity;
import com.internship.service.service.rootdb.MainDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/")
public class MainDatabaseController {
    @Autowired
    private MainDatabaseService mainDataBaseService;

    @PostMapping("databases")
    public void addDataSource(@RequestBody DataSourceEntity dataSourceEntity) {
        mainDataBaseService.addDataSource(dataSourceEntity);
    }

    @DeleteMapping("databases/{name}")
    public void deleteDataSource(@PathVariable("name") String name) {
        mainDataBaseService.removeDataSource(name);
    }

    @GetMapping("databases")
    public List<DataSourceEntity> findAllDatabases() {
        return mainDataBaseService.findAll();
    }

    @GetMapping("databases/{username}")
    public DataSourceEntity findByName(@PathVariable("username") String name) throws SQLException {
        return mainDataBaseService.findByName(name);
    }
}
