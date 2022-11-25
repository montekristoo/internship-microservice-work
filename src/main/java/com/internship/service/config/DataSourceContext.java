package com.internship.service.config;

import com.internship.service.entity.DataSourceEntity;
import com.internship.service.service.rootdb.MainDatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.internship.service.util.DataSourceMapper.*;
import java.sql.SQLException;

@Component
@Slf4j
public class DataSourceContext {
    private final RoutingDataSource routingDataSource;
    private final MainDatabaseService databaseService;
    private final static ThreadLocal<String> context = new ThreadLocal<>();

    public DataSourceContext(RoutingDataSource routingDataSource, MainDatabaseService databaseService) {
        this.routingDataSource = routingDataSource;
        this.databaseService = databaseService;
    }

    public void setContext(String targetDataSource) throws SQLException {
        DataSourceEntity dataSourceEntity = databaseService.findByName(targetDataSource);
        context.set(targetDataSource);
        routingDataSource.addDataSource(targetDataSource, entityToDataSrc(dataSourceEntity));
    }

    public void removeContext() {
        if (context.get() != null) {
            routingDataSource.closeDataSource();
        }
        context.remove();
        log.info("Current context: " + context.get());
    }

    public static String getCurrentContext() {
        return context.get();
    }

}
