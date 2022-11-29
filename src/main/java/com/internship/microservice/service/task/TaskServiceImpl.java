package com.internship.microservice.service.task;

import com.internship.microservice.entity.UserEntity;
import com.internship.microservice.mapper.UserMapper;
import com.internship.microservice.routing.DataSourceContext;
import com.internship.microservice.routing.RoutingDataSource;
import com.internship.microservice.service.datasource.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("ALL")
@EnableScheduling
@Service
@Slf4j
@EnableAspectJAutoProxy(exposeProxy = true)
public class TaskServiceImpl implements TaskService {
    private final DataSourceService dataSourceService;
    private final RoutingDataSource routingDataSource;
    private final DataSourceContext dataSourceContext;
    private final UserMapper userMapper;
    private final int TIMER = 5000;

    @Autowired
    public TaskServiceImpl(DataSourceService dataSourceService, RoutingDataSource routingDataSource,
                           DataSourceContext dataSourceContext, UserMapper userMapper) {
        this.dataSourceService = dataSourceService;
        this.routingDataSource = routingDataSource;
        this.dataSourceContext = dataSourceContext;
        this.userMapper = userMapper;
    }

//    @Scheduled(fixedDelay = TIMER)
    @Override
    public void routing() {
        for (String database : dataSourceService.findCountriesDatabases()) {
            ((TaskService) AopContext.currentProxy()).connect2(database);
        }
    }


    @Override
    public void connect(String name, List<UserEntity> users) {
        log.info("I am connected to " + DataSourceContext.getCurrentContext());
        users.forEach((user) -> {
            userMapper.addUser(user);
        });
    }

    @Override
    public void connect2(String name) {
        dataSourceService.dropTableUsers();
        dataSourceService.createTableUsers();
    }
}
