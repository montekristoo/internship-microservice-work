package com.internship.service.config;

import com.internship.service.entity.DataSourceEntity;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.internship.service.util.DataSourceMapper.*;

@Slf4j
public class RouterDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();
    @Autowired
    @Lazy
    private List<DataSourceEntity> getDbsInfo;
    @Autowired
    @Lazy
    private JdbcTemplate jdbcTemplate;

    private void setDataSource(String name) {
        System.out.println(getDbsInfo);
        DataSourceEntity dataSrcEntity = getDbsInfo.stream()
                .filter(db -> db.getName().equals(name))
                .findFirst()
                .get();
        jdbcTemplate.setDataSource(entityToDataSrc(dataSrcEntity));
    }

    public void setContext(String name) {
        contextHolder.set(name);
        if (name.equals("main_db")) {
            jdbcTemplate.setDataSource(this.getResolvedDataSources().get("main_db"));
            return;
        }
        setDataSource(name);
    }

    public void removeContext() throws SQLException {
        jdbcTemplate.getDataSource()
                .unwrap(HikariDataSource.class)
                .close();
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
