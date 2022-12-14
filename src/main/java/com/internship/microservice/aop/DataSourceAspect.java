package com.internship.microservice.aop;

import com.internship.microservice.routing.DataSourceContext;
import com.internship.microservice.routing.RoutingDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Order(-100)
public class DataSourceAspect {

    @Autowired
    private DataSourceContext dataSourceContext;
    @Autowired
    private RoutingDataSource routingDataSource;

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
        ((java.util.Map) field[0]).forEach((k, v) ->
                routingDataSource.closeDataSource(((String) k).toLowerCase()));
    }
}