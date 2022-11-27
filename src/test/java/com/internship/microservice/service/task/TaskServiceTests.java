package com.internship.microservice.service.task;

import com.internship.microservice.ServiceApplication;
import com.internship.microservice.routing.DataSourceContext;
import com.internship.microservice.service.datasource.DataSourceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ServiceApplication.class)
@ExtendWith(SpringExtension.class)
class TaskServiceTests {
    @Autowired
    private DataSourceContext dataSourceContext;
    @Autowired
    private DataSourceService dataSourceService;

    @Test
    public void givenCurrentDb1ToConnect_thenInsertNewDataInTable_thenCheckIfDataAreInsertedInCorrectDb() throws SQLException {
        dataSourceContext.setContext("db_1");
        String current_db = dataSourceService.getCurrentDatabase();
        dataSourceContext.removeContext();
        assertEquals(current_db, "db_1");
    }

    @Test
    public void givenCurrentDb2ToConnect_thenInsertNewDataInTable_thenCheckIfDataAreInsertedInCorrectDb() throws SQLException {
        dataSourceContext.setContext("db_2");
        String current_db = dataSourceService.getCurrentDatabase();
        dataSourceContext.removeContext();
        assertEquals(current_db, "db_2");
    }

    @Test
    public void givenCurrentDb3ToConnect_thenInsertNewDataInTable_thenCheckIfDataAreInsertedInCorrectDb() throws SQLException {
        dataSourceContext.setContext("db_3");
        String current_db = dataSourceService.getCurrentDatabase();
        dataSourceContext.removeContext();
        assertEquals(current_db, "db_3");
    }

}
