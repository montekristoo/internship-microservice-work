package com.internship.microservice.annotations;

import com.internship.microservice.routing.DataSourceContext;
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

    @Pointcut("execution(* com.internship.microservice.service.routing.RoutingServiceImpl.connect(String, java.util.List))")
    public void methodPointCut() {
    }

    @Before("methodPointCut()")
    public void before(JoinPoint joinPoint) throws Exception {
        Object[] field = joinPoint.getArgs();
        dataSourceContext.setContext((String) field[0]);
        log.info(DataSourceContext.getCurrentContext());
    }

    @After("methodPointCut()")
    public void after() {
        dataSourceContext.removeContext();
    }
}