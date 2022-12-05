package com.internship.microservice.service.task;

import com.internship.microservice.entity.DataSourceEntity;
import com.internship.microservice.exception.DatabaseNotFoundException;
import com.internship.microservice.routing.DataSourceContext;
import com.internship.microservice.service.datasource.DataSourceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataSourceTests {
    @Autowired
    private DataSourceContext dataSourceContext;
    @Autowired
    private DataSourceService dataSourceService;
    @Test
    public void givenCurrentDb1ToConnect_thenCheckIfIsCorrectCurrentDb() {
        dataSourceContext.setContext("uk");
        System.out.println(dataSourceService.getCurrentDatabase());
        String current_db = dataSourceService.getCurrentDatabase();
        dataSourceContext.removeContext();
        assertEquals(current_db, "uk");
    }

    @Test
    public void givenCurrentDb2ToConnect_thenCheckIfIsCorrectCurrentDb() {
        dataSourceContext.setContext("md");
        String current_db = dataSourceService.getCurrentDatabase();
        dataSourceContext.removeContext();
        assertEquals(current_db, "md");
    }

    @Test
    public void givenCurrentDb3ToConnect_thenCheckIfIsCorrectCurrentDb() {
        dataSourceContext.setContext("pt");
        String current_db = dataSourceService.getCurrentDatabase();
        dataSourceContext.removeContext();
        assertEquals(current_db, "pt");
    }

    @Test
    public void givenNotExistingDatabase_thenThrowCustomExceptionToCatchTheError() {
       assertThrows(DatabaseNotFoundException.class, () -> dataSourceContext.setContext("db_90"));
    }

    @Test
    @Transactional
    public void givenNewInsertedDatabase_thenCheckIfItIsHasBeenAdded() {
        DataSourceEntity dataSrc = DataSourceEntity.builder()
                .name("test_db")
                .username("postgres")
                .password("internship")
                .salt(null)
                .jdbcUrl("jdbc:postgresql://localhost:3002/test_db")
                .driverClassName("org.postgresql.Driver")
                .build();

        dataSourceService.addDataSource(dataSrc);

        DataSourceEntity dataSourceEntity = dataSourceService.findByName("test_db");
        assertNotNull(dataSourceEntity);
    }

    @Test
    @Transactional
    public void givenDatabaseToRemove_thenCheckIfItIsHasBeenDeleted() {
        dataSourceService.removeDataSource("ua");
        assertThrows(DatabaseNotFoundException.class, () -> dataSourceService.findByName("ua"));
    }

    @Test
    @Transactional
    public void givenDatabaseToUpdate_thenCheckIfItHasBeenUpdated() {
        DataSourceEntity dataSrc = dataSourceService.findByName("ua");
        String newName = "updated_db";
        dataSrc.setName(newName);
        dataSourceService.updateDatabase(dataSrc, dataSrc.getId());
        DataSourceEntity updatedDataSrc = dataSourceService.findByName(newName);
        Assertions.assertNotNull(updatedDataSrc);
    }
}
