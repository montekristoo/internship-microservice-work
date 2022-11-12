package com.internship.service.dbConfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Stack;

@Slf4j
public class RouterDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setContext(String name) {
        contextHolder.set(name);
    }

//    public static void restoreContext() {
//        Stack<String> context = getContext();
//        if (!context.isEmpty()) {
//            context.pop();
//        }
//    }

    public static String getCurrentSource() {
        return contextHolder.get();
    }

    @Override
    protected Object determineCurrentLookupKey() {
            String context = getContext();
            if (context.isEmpty()) return null;
            String sourceName = context;
            log.info("Current DB {} is working", sourceName);
            return sourceName;
    }

    private static String getContext() {
        return contextHolder.get();
    }
}
