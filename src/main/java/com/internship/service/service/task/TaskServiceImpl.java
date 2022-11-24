package com.internship.service.service.task;

import com.internship.service.config.RoutingDataSource;
import com.internship.service.entity.DataSourceEntity;
import com.internship.service.service.rootdb.MainDatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
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
    private RoutingDataSource routingDataSource;
    @Autowired
    private MainDatabaseService mainDatabaseService;
    @Autowired
    private ApplicationContext applicationContext;
    private static final String INSERT_SQL = "INSERT INTO test_table(description) VALUES ('test_description')";
    private final int TIMER = 2000;

    public TaskServiceImpl() {
    }


    // TODO:
    //- Parcurgerea bazelor de date in metoda connection cu anotatia creata ✔
    //- Redenumirea anotatiei ChangeDatabase in SetDatabase ✔
    //- Crearea unui 'trigger' care va functiona la adaugarea/eliminarea unei baze de date, si care va avea ca scop sa
    //  recreeze bean-ul (aka redefinition bean on right moment) ✔ - Prin cache
    //- Refactorul codului - ✔
    //- Adaugarea driverului - ✔
    //- Eliminarea circuitelor - ✔

    public void routing() throws SQLException {
        for (DataSourceEntity database : mainDatabaseService.findAll()) {
            connect(database.getName());
        }
    }

    public void connect(String name) throws SQLException {
        routingDataSource.setContext(name);
        jdbcTemplate.execute(INSERT_SQL);
        routingDataSource.removeContext();
    }
}
