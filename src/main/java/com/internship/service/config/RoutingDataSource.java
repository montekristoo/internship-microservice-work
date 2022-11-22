package com.internship.service.config;

import com.internship.service.entity.DataSourceEntity;
import com.internship.service.exception.DatabaseNotFoundException;
import com.internship.service.service.rootdb.RootDatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.internship.service.util.DataSourceMapper.*;

@Slf4j
@Component
public class RoutingDataSource {
    @Autowired
    private RootDatabaseService rootDatabaseService;
    @Autowired
    private JdbcTemplateManager jdbcTemplateManager;
    @Autowired
    @Qualifier("defaultDataSource")
    private DataSource defaultDataSource;
    private static final String MAIN_DB = "main_db";
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    private void setCustomDataSource(String name) {
        if (name.equals(MAIN_DB)) {
            setDefaultDatabase();
            return;
        }
        List<DataSourceEntity> entityList = new ArrayList<>(rootDatabaseService.findAll());
        log.info(entityList.toString());
        Optional<DataSourceEntity> dataSrcEntity = entityList.stream()
                .filter(db -> db.getName().equals(name))
                .findFirst();
        if (dataSrcEntity.isEmpty()) {
            throw new DatabaseNotFoundException(String.format("Database %s doesn't exists", name));
        }
        jdbcTemplateManager.setDataSource(entityToDataSrc(dataSrcEntity.get()));
    }

    public void setDefaultDatabase() {
        jdbcTemplateManager.setDataSource(defaultDataSource);
    }

    public void setContext(String name) {
        contextHolder.set(name);
        System.out.println("In set: " + contextHolder.get());
        setCustomDataSource(name);
    }

    public void removeContext() throws SQLException {
        if (contextHolder.get().equals(MAIN_DB)) {
            return;
        }
        jdbcTemplateManager.removeCurrentDataSource();
        contextHolder.remove();
    }


}
