package com.internship.service.service.task;

import com.internship.service.annotations.ChangeDatabase;
import com.internship.service.entity.DataSourceEntity;
import com.internship.service.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("ALL")
@EnableScheduling
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AbstractRoutingDataSource abstractRoutingDataSource;
    private static final String SQL = "INSERT INTO test_table(description) VALUES ('test_description')";
    private final int TIMER = 2000;

    public TaskServiceImpl() {}

    @ChangeDatabase(value = "db_1")
    @Scheduled(fixedDelay = 3000)
    public void insertIntoDb1() {
        jdbcTemplate.execute(SQL);
    }

    @ChangeDatabase(value = "db_2")
    @Scheduled(fixedDelay = 6000)
    public void insertIntoDb2() {
        jdbcTemplate.execute(SQL);
    }

    @ChangeDatabase(value = "db_3")
    @Scheduled(fixedDelay = 7000)
    public void insertIntoDb3() {
        jdbcTemplate.execute(SQL);
    }

//
//    @ChangeDatabase(value = "db_4")
//    @Scheduled(fixedDelay = TIMER)
//    public void insertIntoDb4() {
//        if (!RouterDataSource.getCurrentSource().equals("db_4")) {
//            return;
//        }
//        jdbcTemplate.execute(SQL);
//    }

    @Override
    public List<TaskEntity> getAll() {
        return jdbcTemplate.query("SELECT * FROM test_table;", new BeanPropertyRowMapper<>(TaskEntity.class));
    }

    @Override
    public void truncateTable() {
        jdbcTemplate.execute("TRUNCATE TABLE test_table;");
    }
}
