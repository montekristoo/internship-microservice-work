package com.internship.microservice.controller;

import com.internship.microservice.entity.DataSourceEntity;
import com.internship.microservice.service.datasource.DataSourceService;
import com.internship.microservice.service.task.TaskService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/")
public class MainDatabaseController {

    private final DataSourceService dataSourceService;
    private final TaskService taskService;
    public MainDatabaseController(DataSourceService dataSourceService, TaskService taskService) {
        this.dataSourceService = dataSourceService;
        this.taskService = taskService;
    }

    @PostMapping("databases")
    public void addDataSource(@RequestBody DataSourceEntity dataSourceEntity) {
        dataSourceService.addDataSource(dataSourceEntity);
    }

    @DeleteMapping("databases/{name}")
    public void deleteDataSource(@PathVariable("name") String name) {
        dataSourceService.removeDataSource(name);
    }

    @GetMapping("databases")
    public List<DataSourceEntity> findAllDatabases() {
        return dataSourceService.findAll();
    }

    @GetMapping("databases/{username}")
    public DataSourceEntity findByName(@PathVariable("username") String name) throws SQLException {
        return dataSourceService.findByName(name);
    }

    @PatchMapping("databases")
    public void updateDatabase(@RequestBody DataSourceEntity dataSrc) {
        dataSourceService.updateDatabase(dataSrc, dataSrc.getId());
    }

    @GetMapping("databases/routing")
    public void routing() {
        taskService.routing();
    }
}
