package com.internship.service.dbConfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Slf4j
public class RouterDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setContext(String name) {
        contextHolder.set(name);
    }

    public static String getCurrentSource() {
        return contextHolder.get();
    }

    @Override
    protected Object determineCurrentLookupKey() {
            String context = getContext();
            if (context.isEmpty()) return null;
            log.info("Current DB {} is working ", context);
            return context;
    }

    private static String getContext() {
        return contextHolder.get();
    }
}
