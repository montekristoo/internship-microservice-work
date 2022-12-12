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
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Configuration
@Order(-100)
public class DataSourceAspect {

    @Autowired
    private DataSourceContext dataSourceContext;
    @Autowired
    private RoutingDataSource routingDataSource;

    @Pointcut("execution(* com.internship.microservice.service.user.UserServiceImpl.connect(String, java.util.List))")
    public void contextPointcut() {
    }

    @Pointcut("execution(* com.internship.microservice.service.user.UserServiceImpl.sendToTransactionContainer(java" +
            ".util.Map))")
    public void closeCon() {
    }

    @Before("contextPointcut()")
    public void before(JoinPoint joinPoint) {
        Object[] field = joinPoint.getArgs();
        dataSourceContext.setContext((String) field[0]);
        log.info(DataSourceContext.getCurrentContext());
    }

    @After("contextPointcut()")
    public void afterContext(JoinPoint joinPoint) {
        dataSourceContext.removeContext();
    }

//    @After("closeCon()")
//    public void after(JoinPoint joinPoint) {
//        Object[] field = joinPoint.getArgs();
//
//        ((java.util.HashMap) field[0]).forEach((k, v) -> {
//            routingDataSource.closeDataSource(((String) k).toLowerCase());
//        });
//    }
}