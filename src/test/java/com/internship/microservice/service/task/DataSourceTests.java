package com.internship.microservice.service.task;

import com.internship.microservice.entity.DataSourceEntity;
import com.internship.microservice.exception.DatabaseNotFoundException;
import com.internship.microservice.routing.DataSourceContext;
import com.internship.microservice.service.datasource.DataSourceService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class DataSourceTests {
    @Autowired
    private DataSourceContext dataSourceContext;
    @Autowired
    private DataSourceService dataSourceService;

    @Test
    public void givenCurrentDb1ToConnect_thenCheckIfIsCorrectCurrentDb() throws SQLException {
        dataSourceContext.setContext("db_1");
        String current_db = dataSourceService.getCurrentDatabase();
        dataSourceContext.removeContext();
        assertEquals(current_db, "db_1");
    }

    @Test
    public void givenCurrentDb2ToConnect_thenCheckIfIsCorrectCurrentDb() throws SQLException {
        dataSourceContext.setContext("db_2");
        String current_db = dataSourceService.getCurrentDatabase();
        dataSourceContext.removeContext();
        assertEquals(current_db, "db_2");
    }

    @Test
    public void givenCurrentDb3ToConnect_thenCheckIfIsCorrectCurrentDb() throws SQLException {
        dataSourceContext.setContext("db_3");
        String current_db = dataSourceService.getCurrentDatabase();
        dataSourceContext.removeContext();
        assertEquals(current_db, "db_3");
    }

    @Test
    public void givenNotExistingDatabase_thenThrowCustomExceptionToCatchTheError() {
       assertThrows(DatabaseNotFoundException.class, () -> dataSourceContext.setContext("db_90"));
    }

    @Test
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

        int changedSize = dataSourceService.findAll().size();
        assertEquals(changedSize, 5);
    }

    @Test
    public void givenDatabaseToRemove_thenCheckIfItIsHasBeenDeleted() {
        dataSourceService.removeDataSource("db_1");
        int size = dataSourceService.findAll().size();
        assertEquals(size, 3);
    }

    @SneakyThrows
    @Test
    public void givenDatabaseToUpdate_thenCheckIfItHasBeenUpdated() {
        DataSourceEntity dataSrc = dataSourceService.findByName("db_1");
        String newName = "updated_db";
        dataSrc.setName(newName);
        dataSourceService.updateDatabase(dataSrc, dataSrc.getId());
        DataSourceEntity updatedDataSrc = dataSourceService.findByName(newName);
        Assertions.assertNotNull(updatedDataSrc);
    }
}
