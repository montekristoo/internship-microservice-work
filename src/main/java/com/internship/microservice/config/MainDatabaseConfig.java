package com.internship.microservice.config;

import com.atomikos.icatch.jta.UserTransactionManager;
import com.internship.microservice.entity.DataSourceEntity;
import com.internship.microservice.routing.RoutingDataSource;
import com.internship.microservice.util.DataSourceConverter;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;


@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MainDatabaseConfig {

    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.url}")
    String jdbcUrl;
    @Value("${spring.datasource.driver-class-name}")
    String driverClassName;
    final static String DEFAULT = "main_db";
    final DataSourceConverter dsConverter;

    @Autowired
    public MainDatabaseConfig(DataSourceConverter dsConverter) {
        this.dsConverter = dsConverter;
    }

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

}
