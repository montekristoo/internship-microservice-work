package com.internship.microservice.annotations;

import com.internship.microservice.routing.DataSourceContext;
import com.internship.microservice.routing.RoutingDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Aspect
@Slf4j
@Component
public class DataSourceAspect {

    @Autowired
    private DataSourceContext dataSourceContext;
    @Autowired
    private SqlSessionFactory sessionFactory;
    @Autowired
    private RoutingDataSource routingDataSource;

    @Pointcut("execution(* com.internship.microservice.service.routing.RoutingServiceImpl.connect(String, java.util.List))")
    public void methodPointCut() {
    }

    @Before("methodPointCut()")
    public void before(JoinPoint joinPoint) throws Exception {
        Object[] field = joinPoint.getArgs();
        dataSourceContext.setContext((String) field[0]);
//        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
//        sessionFactoryBean.setDataSource(routingDataSource.determineTargetDataSource());
        log.info(DataSourceContext.getCurrentContext());
    }

    @After("methodPointCut()")
    public void after() {
        dataSourceContext.removeContext();
    }
}