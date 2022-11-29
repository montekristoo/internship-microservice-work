package com.internship.microservice.controller;

import com.internship.microservice.entity.DataSourceEntity;
import com.internship.microservice.service.datasource.DataSourceService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/")
public class DataSourceController {

    private final DataSourceService dataSourceService;

    public DataSourceController(DataSourceService dataSourceService) {
        this.dataSourceService = dataSourceService;
    }

    @PostMapping("databases")
    public void addDataSource(@RequestBody DataSourceEntity dataSource) {
        dataSourceService.addDataSource(dataSource);

    }

    @DeleteMapping("databases/{name}")
    public void deleteDataSource(@PathVariable("name") String name) {
        dataSourceService.removeDataSource(name);
    }

    @GetMapping("databases")
    public List<DataSourceEntity> findAllDatabases() {
        return dataSourceService.findAll();
    }

    @GetMapping("databases/{name}")
    public DataSourceEntity findByName(@PathVariable("name") String name) throws SQLException {
        return dataSourceService.findByName(name);
    }

    @PatchMapping("databases")
    public void updateDatabase(@RequestBody DataSourceEntity dataSrc) {
        dataSourceService.updateDatabase(dataSrc, dataSrc.getId());
    }
}
