package com.internship.service.config;

import com.internship.service.entity.DataSourceEntity;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariConfigMXBean;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jdbc.metadata.HikariDataSourcePoolMetadata;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class RouterDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
    @Autowired
    @Lazy
    private JdbcTemplate jdbcTemplate;

    public void setDataSource1() {
        HikariConfig config = new HikariConfig();
        config.setUsername("postgres");
        config.setPassword("internship");
        config.setJdbcUrl("jdbc:postgresql://localhost:3002/db_1");
        config.setPoolName("db_1_pool");
        jdbcTemplate.setDataSource(new HikariDataSource(config));
    }

    public void setDataSource2() {
        HikariConfig config = new HikariConfig();
        config.setUsername("postgres");
        config.setPassword("internship");
        config.setJdbcUrl("jdbc:postgresql://localhost:3002/db_2");
        config.setPoolName("db_2_pool");
        jdbcTemplate.setDataSource(new HikariDataSource(config));
    }
    public static void setContext(String name) {
        contextHolder.set(name);
    }

    public static String getCurrentSource() {
        return contextHolder.get();
    }

    public void removeContext1() throws SQLException {
        jdbcTemplate.getDataSource().unwrap(HikariDataSource.class).close();
    }

    public void removeContext2() throws SQLException, InterruptedException {
        jdbcTemplate.getDataSource().unwrap(HikariDataSource.class).close();
    }

    @Override
    protected Object determineCurrentLookupKey() {
            System.out.println("Current context:" + contextHolder.get());
            if (contextHolder.get() == null) return null;
//            log.info("Current DB {} is working ", context);
            return contextHolder.get();
    }

    @Override
    public Map<Object, DataSource> getResolvedDataSources() {
        return super.getResolvedDataSources();
    }

}
