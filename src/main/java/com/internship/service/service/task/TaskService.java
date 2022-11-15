package com.internship.service.service.task;

import com.internship.service.entity.TaskEntity;

import java.util.List;


public interface TaskService {
    void insertIntoDb1();
    void insertIntoDb2();
    void insertIntoDb3();
    List<TaskEntity> getAll();
    void truncateTable();
    String getCurrentDb();

}
