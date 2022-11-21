package com.internship.service.service.task;

import com.internship.service.config.DatabasePullerManager;
import com.internship.service.config.RoutingDataSource;
import com.internship.service.entity.DataSourceEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
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
    private AbstractRoutingDataSource abstractRoutingDataSource;
    @Autowired
    private DatabasePullerManager databasePullerManager;
    @Autowired
    private ApplicationContext applicationContext;
    private static final String SQL = "INSERT INTO test_table(description) VALUES ('test_description')";
    private final int TIMER = 2000;

    public TaskServiceImpl() {
    }


    // TODO:
    //- Parcurgerea bazelor de date in metoda connection cu anotatia creata ✔
    //- Redenumirea anotatiei ChangeDatabase in setDatabase ✔
    //- Crearea unui 'trigger' care va functiona la adaugarea/eliminarea unei baze de date, si care va avea ca scop sa
    //  recreeze bean-ul (aka redefinition bean on right moment)
    //- Refactorul codului - ✔
    //- Adaugarea driverului - ✔
    //-

    @Scheduled(fixedDelay = 3000)
    public void routing() {
        for (DataSourceEntity database : databasePullerManager.dataSourceEntities()) {
            connect(database.getName());
        }
        log.info(applicationContext.getBean(DatabasePullerManager.class).toString());
    }

    public void connect(String name) {
        ((RoutingDataSource) abstractRoutingDataSource).setContext(name);
        jdbcTemplate.execute(SQL);
        try {
            ((RoutingDataSource) abstractRoutingDataSource).removeContext();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


//    @Override
//    public List<TaskEntity> getAll() {
//        return jdbcTemplate.query("SELECT * FROM test_table;", new BeanPropertyRowMapper<>(TaskEntity.class));
//    }
//
//    @Override
//    public void truncateTable() {
//        jdbcTemplate.execute("TRUNCATE TABLE test_table;");
//    }
}
