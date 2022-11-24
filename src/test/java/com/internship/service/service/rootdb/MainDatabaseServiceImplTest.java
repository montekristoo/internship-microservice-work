package com.internship.service.service.rootdb;

import com.internship.service.ServiceApplication;
import com.internship.service.config.RoutingDataSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ServiceApplication.class)
@ExtendWith(SpringExtension.class)
class MainDatabaseServiceImplTest {

    @Autowired
    private MainDatabaseService dsSource;
    @Autowired
    private RoutingDataSource routingDataSource;


    @Test
    public void givenCurrentDb1_CheckCurrentDb() {
        String db = "db_1";
        routingDataSource.setContext(db);
        String result = dsSource.getCurrentDb();
        assertEquals(result, db);
    }

    @Test
    public void givenCurrentDb2_CheckCurrentDb() {
        String db = "db_2";
        routingDataSource.setContext(db);
        String result = dsSource.getCurrentDb();
        assertEquals(result, db);
    }

    @Test
    public void givenCurrentDb3_CheckCurrentDb() {
        String db = "db_3";
        routingDataSource.setContext(db);
        String result = dsSource.getCurrentDb();
        assertEquals(result, db);
    }

//    @Test
//    void removeDataSource() throws Exception {
//        mockServer.expect(ExpectedCount.once(), requestTo(new URI(baseUrl + "/db_4")))
//                .andExpect(method(HttpMethod.DELETE))
//                .andRespond(withStatus(HttpStatus.OK));
//        assertEquals(dsSource.findAll().size(), 4);
//    }
//
//    @Test
//    void addDataSource() throws Exception {
//        DataSourceEntity newDataSource = new DataSourceEntity("db_4", "postgres", "internship", "jdbc:postgresql://localhost:3002/db_4");
//        mockServer.expect(ExpectedCount.once(), requestTo(new URI(baseUrl)))
//                .andExpect(method(HttpMethod.POST))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(mapper.writeValueAsString(newDataSource)));
//        assertEquals(dsSource.findAll().size(), 4);
//    }
}