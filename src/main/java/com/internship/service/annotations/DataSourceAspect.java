package com.internship.service.annotations;

import com.internship.service.config.RouterDataSource;
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

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.SQLException;

@Aspect
@Slf4j
@Component
public class DataSourceAspect {
    @Autowired
    private AbstractRoutingDataSource routingDataSource;

    @Pointcut("@annotation(com.internship.service.annotations.ChangeDatabase)")
    public void annotationPointCut() {
    }

    @Before("annotationPointCut()")
    public void before(JoinPoint joinPoint) throws SQLException {
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        ChangeDatabase annotation = method.getAnnotation(ChangeDatabase.class);
        ((RouterDataSource) routingDataSource).setContext(annotation.value());
    }

    @After("annotationPointCut()")
    public void after(JoinPoint joinPoint) throws SQLException {
        ((RouterDataSource) routingDataSource).removeContext();
    }

}
