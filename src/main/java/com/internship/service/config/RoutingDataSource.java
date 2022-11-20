package com.internship.service.config;

import com.internship.service.entity.DataSourceEntity;
import com.internship.service.service.rootdb.RootDatabaseService;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.internship.service.util.DataSourceMapper.*;

public class RoutingDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
    @Autowired
    private RootDatabaseService dataSrcService;
    @Autowired
    @Lazy
    private JdbcTemplate jdbcTemplate;

    private void setDataSource(String name) {
        List<DataSourceEntity> list = dataSrcService.findAll();
        System.out.println(list);
        DataSourceEntity dataSrcEntity = list.stream()
                .filter(db -> db.getName().equals(name))
                .findFirst()
                .get();
        jdbcTemplate.setDataSource(entityToDataSrc(dataSrcEntity));
    }

    public void setContext(String name) throws SQLException {
        System.out.println("In set context: " + contextHolder.get());
        if (name.equals("main_db")) {
            System.out.println("Before:" + jdbcTemplate.getDataSource());
            jdbcTemplate.getDataSource().unwrap(HikariDataSource.class).close();
            System.out.println(jdbcTemplate.getDataSource());
            contextHolder.set(name);
            System.out.println("After: ");
            return;
        }
        setDataSource(name);
    }

    public void removeContext() throws SQLException {
        if (contextHolder.get().equals("main_db")) {
            contextHolder.remove();
            return;
        }
        jdbcTemplate.getDataSource()
                .unwrap(HikariDataSource.class)
                .close();
        contextHolder.remove();
    }

    @Override
    protected Object determineCurrentLookupKey() {
            if (contextHolder.get() == null) return "main_db";
//            log.info("Current DB {} is working ", context);
            return contextHolder.get();
    }

    @Override
    public Map<Object, DataSource> getResolvedDataSources() {
        return super.getResolvedDataSources();
    }

}
