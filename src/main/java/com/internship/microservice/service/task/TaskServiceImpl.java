package com.internship.microservice.service.task;

import com.internship.microservice.entity.DataSourceEntity;
import com.internship.microservice.entity.TaskEntity;
import com.internship.microservice.routing.DataSourceContext;
import com.internship.microservice.routing.RoutingDataSource;
import com.internship.microservice.service.datasource.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@SuppressWarnings("ALL")
@EnableScheduling
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final DataSourceService dataSourceService;
    private final RoutingDataSource routingDataSource;
    private final DataSourceContext dataSourceContext;
    private final TaskService taskService;
    private final int TIMER = 5000;

    @Autowired
    public TaskServiceImpl(DataSourceService dataSourceService, RoutingDataSource routingDataSource,
                           DataSourceContext dataSourceContext, @Lazy TaskService taskService) {
        this.dataSourceService = dataSourceService;
        this.routingDataSource = routingDataSource;
        this.dataSourceContext = dataSourceContext;
        this.taskService = taskService;
    }

    @Scheduled(fixedDelay = TIMER)
    public void routing() {
        for (DataSourceEntity database : dataSourceService.findAll()) {
            taskService.connect(database.getName());
        }
    }
    public void connect(String name) {
        dataSourceService.addTestData(new TaskEntity(null, "test"));
    }
}
