package com.internship.service.service.task;

import com.internship.service.entity.TaskEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskService {
//    void insertIntoDb1();
//    void insertIntoDb2();
//    void insertIntoDb3();
    List<TaskEntity> getAll();
    void truncateTable();

}
