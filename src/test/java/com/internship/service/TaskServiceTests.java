package com.internship.service;

import com.internship.service.dbConfig.RouterDataSource;
import com.internship.service.entity.TaskEntity;
import com.internship.service.service.restart.RestartService;
import com.internship.service.service.task.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TaskServiceTests {

    @Autowired
    private TaskService taskService;

    @BeforeEach
    void clearTables() {
        RouterDataSource.setContext("db_1");
        taskService.truncateTable();
        RouterDataSource.setContext("db_2");
        taskService.truncateTable();
        RouterDataSource.setContext("db_3");
        taskService.truncateTable();
        RouterDataSource.setContext("db_4");
        taskService.truncateTable();
    }

    @Test
    public void givenCurrentDb1_CheckCurrentDb() {
      RouterDataSource.setContext("db_1");
      String result = taskService.getCurrentDb();
      assertEquals(result, "db_1");
    }

    @Test
    public void givenCurrentDb2_CheckCurrentDb() {
        RouterDataSource.setContext("db_2");
        String result = taskService.getCurrentDb();
        assertEquals(result, "db_2");
    }

    @Test
    public void givenCurrentDb3_CheckCurrentDb() {
        RouterDataSource.setContext("db_3");
        String result = taskService.getCurrentDb();
        assertEquals(result, "db_3");
    }

    @Test
    public void givenCurrentDb1ToConnect_thenInsertNewDataInTable_thenCheckIfDataAreInsertedInCorrectDb() throws SQLException {
        taskService.insertIntoDb1();
        List<TaskEntity> tasksFromDb1 = taskService.getAll();
        assertEquals(tasksFromDb1.size(), 1);
    }

    @Test
    public void givenCurrentDb2ToConnect_thenInsertNewDataInTable_thenCheckIfDataAreInsertedInCorrectDb() {
        taskService.insertIntoDb2();
        List<TaskEntity> tasksFromDb2 = taskService.getAll();
        assertEquals(tasksFromDb2.size(), 1);
    }

    @Test
    public void givenCurrentDb3ToConnect_thenInsertNewDataInTable_thenCheckIfDataAreInsertedInCorrectDb() {
        taskService.insertIntoDb3();
        List<TaskEntity> tasksFromDb3 = taskService.getAll();
        assertEquals(tasksFromDb3.size(), 1);
    }



}
