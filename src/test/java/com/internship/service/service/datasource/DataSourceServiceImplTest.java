package com.internship.service.service.datasource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.service.ServiceApplication;
import com.internship.service.config.RouterDataSource;
import com.internship.service.entity.DataSourceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest(classes = ServiceApplication.class)
@ExtendWith(SpringExtension.class)
class DataSourceServiceImplTest {

    @Autowired
    private DataSourceService dsSource;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper mapper;

    private MockRestServiceServer mockServer;
    private final String baseUrl = "http://localhost:8080/databases";

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void findAll() {
    }

    @Test
    void removeDataSource() throws Exception {
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(baseUrl + "/db_4")))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK));
        assertEquals(dsSource.findAll().size(), 4);
    }

    @Test
    void addDataSource() throws Exception {
        DataSourceEntity newDataSource = new DataSourceEntity("db_4", "postgres", "internship", "jdbc:postgresql://localhost:3002/db_4");
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(baseUrl)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(newDataSource)));
        assertEquals(dsSource.findAll().size(), 4);
    }


    @Test
    public void givenCurrentDb1_CheckCurrentDb() {
        String db = "db_1";
        RouterDataSource.setContext(db);
        String result = dsSource.getCurrentDb();
        assertEquals(result, db);
    }

    @Test
    public void givenCurrentDb2_CheckCurrentDb() {
        String db = "db_2";
        RouterDataSource.setContext(db);
        String result = dsSource.getCurrentDb();
        assertEquals(result, db);
    }

    @Test
    public void givenCurrentDb3_CheckCurrentDb() {
        String db = "db_3";
        RouterDataSource.setContext(db);
        String result = dsSource.getCurrentDb();
        assertEquals(result, db);
    }
}