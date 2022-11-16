package com.internship.service.controller;

import com.internship.service.annotations.ChangeDatabase;
import com.internship.service.entity.DataSourceEntity;
import com.internship.service.service.datasource.DataSourceService;
import com.internship.service.service.restart.RestartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class DataSourceController {
    private final DataSourceService dataSourceService;
    private final RestartServiceImpl restartServiceImpl;

    @Autowired
    public DataSourceController(DataSourceService dataSourceService, RestartServiceImpl restartServiceImpl) {
        this.dataSourceService = dataSourceService;
        this.restartServiceImpl = restartServiceImpl;
    }

    @ChangeDatabase(value = "main_db")
    @PostMapping("databases")
    public void addDataSource(@RequestBody DataSourceEntity dataSourceEntity) {
        System.out.println(dataSourceEntity.getName());
        dataSourceService.addDataSource(dataSourceEntity);
        restartServiceImpl.restartApp();
    }

    @DeleteMapping("databases/{name}")
    @ChangeDatabase(value = "main_db")
    public void deleteDataSource(@PathVariable("name") String name) {
        dataSourceService.removeDataSource(name);
        restartServiceImpl.restartApp();
    }
}
