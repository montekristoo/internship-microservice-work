package com.internship.service.annotations;

import com.internship.service.config.RoutingDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.sql.SQLException;

@Aspect
@Slf4j
@Component
public class DataSourceAspect {
    @Autowired
    private AbstractRoutingDataSource routingDataSource;

    @Pointcut("@annotation(com.internship.service.annotations.SetDatabase)")
    public void annotationPointCut() {
    }

    @Before("annotationPointCut()")
    public void before(JoinPoint joinPoint) throws SQLException {
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        SetDatabase annotation = method.getAnnotation(SetDatabase.class);
        ((RoutingDataSource) routingDataSource).setContext(annotation.value());
    }

    @After("annotationPointCut()")
    public void after(JoinPoint joinPoint) throws SQLException {
        ((RoutingDataSource) routingDataSource).removeContext();
    }

}
