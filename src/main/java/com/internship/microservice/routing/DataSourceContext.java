package com.internship.microservice.routing;

import com.internship.microservice.entity.DataSourceEntity;
import com.internship.microservice.service.datasource.DataSourceService;
import com.internship.microservice.util.DataSourceConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
@Slf4j
public class DataSourceContext {
    private final RoutingDataSource routingDataSource;
    private final DataSourceService mainDbService;
    private final DataSourceConverter dsConverter;
    private final static ThreadLocal<String> context = new ThreadLocal<>();

    @Autowired
    public DataSourceContext(RoutingDataSource routingDataSource, DataSourceService mainDbService, DataSourceConverter dsConverter) {
        this.routingDataSource = routingDataSource;
        this.mainDbService = mainDbService;
        this.dsConverter = dsConverter;
    }


    public void setContext(String targetDataSource) {
        DataSourceEntity dataSourceEntity = mainDbService.findByName(targetDataSource);
        DataSource dataSource = dsConverter.entityToDataSource(dataSourceEntity);
        routingDataSource.addDataSource(targetDataSource, dataSource);
        context.set(targetDataSource);
    }

    public void removeContext() {
        context.remove();
    }

    public static String getCurrentContext() {
        return context.get();
    }
}
