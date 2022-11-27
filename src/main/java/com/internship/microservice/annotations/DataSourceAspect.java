package com.internship.microservice.annotations;

import com.internship.microservice.routing.DataSourceContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Aspect
@Slf4j
@Component
public class DataSourceAspect {

    private final DataSourceContext dataSourceContext;

    public DataSourceAspect(DataSourceContext dataSourceContext) {
        this.dataSourceContext = dataSourceContext;
    }

    @Pointcut("execution(* com.internship.microservice.service.task.TaskServiceImpl.connect(String))")
    public void methodPointCut() {
    }

    @Before("methodPointCut()")
    public void before(JoinPoint joinPoint) throws SQLException {
        Object[] field = joinPoint.getArgs();
        dataSourceContext.setContext((String) field[0]);
    }

    @After("methodPointCut()")
    public void after() {
        dataSourceContext.removeContext();
    }
}