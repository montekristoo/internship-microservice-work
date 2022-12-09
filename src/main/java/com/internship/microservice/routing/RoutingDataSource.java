package com.internship.microservice.routing;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {

    private static final Map<Object, DataSource> sources = new HashMap<>();


    @Override
    public Object determineCurrentLookupKey() {
        if (DataSourceContext.getCurrentContext() == null) {
            return null;
        }

        return DataSourceContext.getCurrentContext();
    }

    public void addDataSource(String name, DataSource source) {
        log.info("Name entry: " + name);
        sources.put(name, source);
        System.out.println(sources);
        Map<Object, Object> dataSourcesToObjects = new HashMap<>(sources);
        setTargetDataSources(dataSourcesToObjects);
        afterPropertiesSet();
    }


    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
    }

    @Override
    public DataSource getResolvedDefaultDataSource() {
        return super.getResolvedDefaultDataSource();
    }

    @Override
    public DataSource determineTargetDataSource() {
        return super.determineTargetDataSource();
    }

    @SneakyThrows
    public void closeDataSource(String name) {
        DataSource dataSource = determineTargetDataSource();
        AtomikosDataSourceBean atomikosDataSourceBean = dataSource.unwrap(AtomikosDataSourceBean.class);
        atomikosDataSourceBean.close();
        sources.remove(name);
    }
}
