package com.internship.microservice.aop;

import com.internship.microservice.entity.UserEntity;
import com.internship.microservice.routing.DataSourceContext;
import com.internship.microservice.routing.RoutingDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Aspect
@Component
@Order(-100)
public class DataSourceAspect {

    private final DataSourceContext dataSourceContext;
    private final RoutingDataSource routingDataSource;

    public DataSourceAspect(DataSourceContext dataSourceContext, RoutingDataSource routingDataSource) {
        this.dataSourceContext = dataSourceContext;
        this.routingDataSource = routingDataSource;
    }

    @Pointcut("execution(* com.internship.microservice.service.routing.RoutingServiceImpl.connect(String, java.util" +
            ".List))")
    public void connectPointcut() {
    }

    @Pointcut("execution(* com.internship.microservice.service.user.UserServiceImpl.insertUsersInGlobalTransaction(java" +
            ".util.Map))")
    public void closeAtomikosPoolsFromGlobalTransaction() {
    }

    @Before("connectPointcut()")
    public void beforeConnect(JoinPoint joinPoint) {
        Object[] field = joinPoint.getArgs();
        dataSourceContext.setContext((String) field[0]);
        log.info(DataSourceContext.getCurrentContext());
    }

    @After("connectPointcut()")
    public void afterConnect() {
        dataSourceContext.removeContext();
    }

    @After("closeAtomikosPoolsFromGlobalTransaction()")
    public void afterGlobalTransaction(JoinPoint joinPoint) {
        Object[] field = joinPoint.getArgs();
        Map<String, List<UserEntity>> databases = (Map<String, List<UserEntity>>) field[0];
        databases.forEach((k, v) ->
                routingDataSource.closeDataSource(k.toLowerCase()));
    }
}