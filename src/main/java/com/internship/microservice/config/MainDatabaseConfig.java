package com.internship.microservice.config;

import com.atomikos.icatch.jta.UserTransactionManager;
import com.internship.microservice.entity.DataSourceEntity;
import com.internship.microservice.routing.RoutingDataSource;
import com.internship.microservice.util.DataSourceConverter;
import lombok.SneakyThrows;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;


@Configuration
public class MainDatabaseConfig {

    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    private final static String DEFAULT = "main_db";
    @Autowired
    private DataSourceConverter dsConverter;

    @Bean
    @Primary
    public RoutingDataSource abstractRoutingDataSource()  {
        final RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(defaultDataSource());
        routingDataSource.addDataSource(DEFAULT, defaultDataSource());
        return routingDataSource;
    }

    public DataSource defaultDataSource() {
        DataSourceEntity dataSourceEntity = DataSourceEntity.builder()
                .name(DEFAULT)
                .username(username)
                .password(password)
                .jdbcUrl(jdbcUrl)
                .driverClassName(driverClassName)
                .build();
        return dsConverter.entityToDataSource(dataSourceEntity);
    }

    @SneakyThrows
    @Bean
    public UserTransactionManager userTransaction() {
      UserTransactionManager userTransactionManager = new UserTransactionManager();
      userTransactionManager.setForceShutdown(false);
      return userTransactionManager;
    }

    @Bean
    public JtaTransactionManager jtaTransactionManager() {
        return new JtaTransactionManager(userTransaction(), userTransaction());
    }

    @Bean
    public SqlSessionFactoryBean sessionFactoryBean() {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(abstractRoutingDataSource());
        sessionFactoryBean.setTransactionFactory(new ManagedTransactionFactory());
        return sessionFactoryBean;
    }

    @Bean
    public SqlSessionTemplate batchSqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(Objects.requireNonNull(sessionFactoryBean().getObject()), ExecutorType.BATCH);
    }

}
