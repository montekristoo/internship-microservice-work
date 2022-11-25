package com.internship.service.service.task;

import com.internship.service.config.DataSourceContext;
import com.internship.service.config.RoutingDataSource;
import com.internship.service.entity.DataSourceEntity;
import com.internship.service.service.rootdb.MainDatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@SuppressWarnings("ALL")
@EnableScheduling
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MainDatabaseService mainDatabaseService;
    @Autowired
    private RoutingDataSource routingDataSource;
    @Autowired
    private DataSourceContext dataSourceContext;
    private static final String INSERT_SQL = "INSERT INTO test_table(description) VALUES ('test_description')";
    private final int TIMER = 2000;

    public TaskServiceImpl() {
    }

    @Scheduled(fixedDelay = 3000)
    public void routing() throws SQLException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException {
        for (DataSourceEntity database : mainDatabaseService.findAll()) {
             connect(database.getName());
        }
    }

    public void connect(String name) throws SQLException {
        dataSourceContext.setContext(name);
        dataSourceContext.removeContext();
    }

}
