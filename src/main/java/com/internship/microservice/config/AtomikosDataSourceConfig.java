////package com.internship.microservice.config;
////
////import com.atomikos.icatch.jta.UserTransactionManager;
////import com.atomikos.jdbc.AbstractDataSourceBean;
////import com.atomikos.jdbc.AtomikosDataSourceBean;
////import com.internship.microservice.routing.RoutingDataSource;
////import lombok.SneakyThrows;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.transaction.annotation.EnableTransactionManagement;
////import org.springframework.transaction.jta.JtaTransactionManager;
////
////import javax.sql.XADataSource;
////import javax.transaction.SystemException;
////
//@Configuration
//@EnableTransactionManagement
//public class AtomikosDataSourceConfig {
//
//    @Bean(initMethod = "init", destroyMethod = "close")
//    public AtomikosDataSourceBean defaultAtomikosDataSource(@Autowired RoutingDataSource routingDataSource) {
//        AtomikosDataSourceBean dataSourceBean = new AtomikosDataSourceBean();
//        dataSourceBean.setXaDataSource((XADataSource) routingDataSource.getResolvedDefaultDataSource());
//        dataSourceBean.re
//        return dataSourceBean;
//    }
//
//    @SneakyThrows
//    @Bean(initMethod = "init", destroyMethod = "close")
//    public UserTransactionManager userTransactionManager() throws SystemException {
//        UserTransactionManager userTransactionManager = new UserTransactionManager();
//        userTransactionManager.setTransactionTimeout(300);
//        userTransactionManager.setForceShutdown(true);
//        return userTransactionManager;
//    }
//
//    @Bean
//    public JtaTransactionManager jtaTransactionManager() throws SystemException {
//        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
//        jtaTransactionManager.setTransactionManager(userTransactionManager());
//        jtaTransactionManager.setUserTransaction(userTransactionManager());
//        return jtaTransactionManager;
//    }
//
//}
