package com.internship.microservice.service.task;

import com.internship.microservice.entity.UserEntity;

import java.util.List;

public interface TaskService {
    void connect(String name, List<UserEntity> dataSourceEntities);
    void connect2(String name);
    void routing();
}