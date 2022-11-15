package com.internship.service.service.task;

import com.internship.service.annotations.ChangeDatabase;
import com.internship.service.dbConfig.RouterDataSource;
import com.internship.service.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@EnableScheduling
@Service
public class TaskServiceImpl implements TaskService {
    private final JdbcTemplate jdbcTemplate;
    private static final String SQL = "INSERT INTO test_table(description) VALUES ('test_description')";
    private final int TIMER = 2000;

    @Autowired
    public TaskServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @ChangeDatabase(value = "db_1")
    @Scheduled(fixedDelay = TIMER)
    public void insertIntoDb1() {
        jdbcTemplate.execute(SQL);
    }

    @ChangeDatabase(value = "db_2")
    @Scheduled(fixedDelay = TIMER)
    public void insertIntoDb2() {
        jdbcTemplate.execute(SQL);
    }

    @ChangeDatabase(value = "db_3")
    @Scheduled(fixedDelay = TIMER)
    public void insertIntoDb3() {
        jdbcTemplate.execute(SQL);
    }

    @ChangeDatabase(value = "db_4")
    @Scheduled(fixedDelay = TIMER)
    public void insertIntoDb4() {
        if (!RouterDataSource.getCurrentSource().equals("db_4")) {
            return;
        }
        jdbcTemplate.execute(SQL);
    }

    @Override
    public List<TaskEntity> getAll() {
        return jdbcTemplate.query("SELECT * FROM test_table;", new BeanPropertyRowMapper<>(TaskEntity.class));
    }

    @Override
    public void truncateTable() {
        jdbcTemplate.execute("TRUNCATE TABLE test_table;");
    }

    @Override
    public String getCurrentDb() {
        return jdbcTemplate.queryForObject("SELECT current_database()", String.class);
    }
}
