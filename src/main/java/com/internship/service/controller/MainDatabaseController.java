package com.internship.service.controller;

import com.internship.service.entity.DataSourceEntity;
import com.internship.service.service.rootdb.MainDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class MainDatabaseController {
    @Autowired
    private MainDatabaseService mainDataBaseService;


    @PostMapping("databases")
    public void addDataSource(@RequestBody DataSourceEntity dataSourceEntity) {
        System.out.println(dataSourceEntity.getName());
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

    @GetMapping("cached/{name}")
    public DataSourceEntity getCachedValue(@PathVariable("name") String name) {
        return mainDataBaseService.getCachedDb(name);
    }

    @GetMapping("databases/{username}")
    public DataSourceEntity findByName(@PathVariable("username") String name) {
        return mainDataBaseService.findByName(name);
    }
}
