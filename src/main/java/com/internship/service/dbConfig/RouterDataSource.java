package com.internship.service.dbConfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

@Slf4j
public class RouterDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setContext(String name) {
        contextHolder.set(name);
    }

    public static String getCurrentSource() {
        return contextHolder.get();
    }

    public static void removeContext() {
        contextHolder.remove();
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
