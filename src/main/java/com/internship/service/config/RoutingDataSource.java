package com.internship.service.config;

import com.internship.service.entity.DataSourceEntity;
import com.internship.service.service.rootdb.MainDatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

import static com.internship.service.util.DataSourceMapper.*;

@Slf4j
@Component
public class RoutingDataSource {
    @Autowired
    private MainDatabaseService mainDatabaseService;
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
        DataSourceEntity dataSource = mainDatabaseService.findByName(name);
        jdbcTemplateManager.setDataSource(entityToDataSrc(dataSource));
    }

    public void setDefaultDatabase() {
        jdbcTemplateManager.setDataSource(defaultDataSource);
    }

    public void setContext(String name) {
        if (contextHolder.get() != null && contextHolder.get().equals(name)) {
            return;
        }
        contextHolder.set(name);
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
