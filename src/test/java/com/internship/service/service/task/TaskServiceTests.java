package com.internship.service.service.task;

import com.internship.service.annotations.ChangeDatabase;
import com.internship.service.dbConfig.RouterDataSource;
import com.internship.service.entity.TaskEntity;
import com.internship.service.service.task.TaskService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

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
    }

    @Test
    public void givenCurrentDb1ToConnect_thenInsertNewDataInTable_thenCheckIfDataAreInsertedInCorrectDb()  {
        taskService.insertIntoDb1();
        List<TaskEntity> tasksFromDb1 = taskService.getAll();
        assertEquals(tasksFromDb1.size(), 1);
    }

    @Test
    @ChangeDatabase(value = "db_2")
    public void givenCurrentDb2ToConnect_thenInsertNewDataInTable_thenCheckIfDataAreInsertedInCorrectDb() {
        RouterDataSource.setContext("db_2");
        taskService.insertIntoDb2();
        List<TaskEntity> tasksFromDb2 = taskService.getAll();
        assertEquals(tasksFromDb2.size(), 1);
    }

    @Test
    @ChangeDatabase(value = "db_3")
    public void givenCurrentDb3ToConnect_thenInsertNewDataInTable_thenCheckIfDataAreInsertedInCorrectDb() {
        RouterDataSource.setContext("db_3");
        taskService.insertIntoDb3();
        List<TaskEntity> tasksFromDb3 = taskService.getAll();
        assertEquals(tasksFromDb3.size(), 1);
    }

}
