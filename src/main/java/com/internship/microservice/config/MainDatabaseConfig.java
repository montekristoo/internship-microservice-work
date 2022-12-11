package com.internship.microservice.config;

import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.internship.microservice.routing.RoutingDataSource;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
public class MainDatabaseConfig {

    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    private final static String DEFAULT = "main_db";

    @Bean
    @Primary
    public RoutingDataSource abstractRoutingDataSource()  {
        final RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(defaultDataSource());
        routingDataSource.addDataSource(DEFAULT, defaultDataSource());
        return routingDataSource;
    }

    public DataSource defaultDataSource() {
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setUniqueResourceName("default");
        ds.setXaDataSourceClassName("org.postgresql.xa.PGXADataSource");
        Properties p = new Properties();
        p.setProperty("user", username);
        p.setProperty("password", password);
        p.setProperty("serverName", "localhost");
        p.setProperty("portNumber", "5432");
        p.setProperty("databaseName", "main_db");
        ds.setXaProperties(p);
        ds.setPoolSize(5);
        return ds;
    }

    @SneakyThrows
    @Bean
    public UserTransactionManager userTransaction() {
      UserTransactionManager userTransactionManager = new UserTransactionManager();
      userTransactionManager.setForceShutdown(false);
      return userTransactionManager;
    }

    @Bean
    public PlatformTransactionManager jtaTransactionManager() {
        return new JtaTransactionManager(userTransaction(), userTransaction());
    }

}
