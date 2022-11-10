package com.mall.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.Map;

@RestController
@RequestMapping("/")
public class DataSourceController {
    @Autowired
    private Map<String, DataSource> dataSourceMap;

    @GetMapping("datasource")
    public void getAllDataSources() {
        dataSourceMap.forEach((k, v) -> {
            System.out.println(k + " "+ v);
        });
    }

}
