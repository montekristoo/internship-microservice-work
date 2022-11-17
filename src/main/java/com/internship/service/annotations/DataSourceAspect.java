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
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.reflect.Method;

@Aspect
@Slf4j
@Component
public class DataSourceAspect {
    private final DataSource dataSource;

    @Autowired
    public DataSourceAspect(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Pointcut("@annotation(com.internship.service.annotations.ChangeDatabase)")
    public void annotationPointCut() {
    }

    @Before("annotationPointCut()")
    public void before(JoinPoint joinPoint) {
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        ChangeDatabase annotation = method.getAnnotation(ChangeDatabase.class);
        if (((RouterDataSource) dataSource).getResolvedDataSources().containsKey(annotation.value())) {
            RouterDataSource.setContext(annotation.value());
        }
        else {
            RouterDataSource.setContext("main_db");
        }
    }

    @After("annotationPointCut()")
    public void after(JoinPoint joinPoint) {
        if (RouterDataSource.getCurrentSource() != null) {
            RouterDataSource.removeContext();
        }
    }

}
