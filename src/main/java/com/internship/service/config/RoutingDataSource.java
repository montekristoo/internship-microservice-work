package com.internship.service.config;

import com.internship.service.entity.DataSourceEntity;
import com.internship.service.exception.DatabaseNotFoundException;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.internship.service.util.DataSourceMapper.*;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {

    private static final String MAIN_DB = "main_db";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
    @Autowired
    private DatabasePullerManager databasePullerManager;
    @Autowired
    @Lazy
    private JdbcTemplate jdbcTemplate;

    private void setDataSource(String name) {
        List<DataSourceEntity> entityList = new ArrayList<>(databasePullerManager.dataSourceEntities());
        log.info(entityList.toString());
        Optional<DataSourceEntity> dataSrcEntity = entityList.stream()
                .filter(db -> db.getName().equals(name))
                .findFirst();
        if (dataSrcEntity.isEmpty()) {
            throw new DatabaseNotFoundException(String.format("Database %s doesn't exists", name));
        }
        jdbcTemplate.setDataSource(entityToDataSrc(dataSrcEntity.get()));
    }

    public void setContext(String name) {
        contextHolder.set(name);
        if (name.equals(MAIN_DB)) {
            jdbcTemplate.setDataSource(this.getResolvedDefaultDataSource());
            return;
        }
        setDataSource(name);
    }

    public void removeContext() throws SQLException {
        if (contextHolder.get().equals(MAIN_DB)) {
            return;
        }
        jdbcTemplate.getDataSource()
                .unwrap(HikariDataSource.class)
                .close();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        if (contextHolder.get() == null) return null;
//            log.info("Current DB {} is working ", context);
        return contextHolder.get();
    }

    @Override
    public Map<Object, DataSource> getResolvedDataSources() {
        return super.getResolvedDataSources();
    }

}
