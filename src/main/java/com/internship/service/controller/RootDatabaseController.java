package com.internship.service.controller;

import com.internship.service.entity.DataSourceEntity;
import com.internship.service.service.rootdb.RootDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class RootDatabaseController {
    @Autowired
    private RootDatabaseService rootDataBaseService;


    @PostMapping("databases")
    public void addDataSource(@RequestBody DataSourceEntity dataSourceEntity) {
        System.out.println(dataSourceEntity.getName());
        rootDataBaseService.addDataSource(dataSourceEntity);
    }

    @DeleteMapping("databases/{name}")
    public void deleteDataSource(@PathVariable("name") String name) {
        rootDataBaseService.removeDataSource(name);
    }
}
