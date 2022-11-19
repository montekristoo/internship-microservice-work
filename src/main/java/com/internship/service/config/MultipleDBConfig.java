package com.internship.service.config;

import com.internship.service.entity.DataSourceEntity;
import com.internship.service.service.datasource.DataSourceService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class MultipleDBConfig {

    @Autowired
    @Lazy
    private JdbcTemplate jdbcTemplate;


    @Bean
    @Primary
    public RouterDataSource getDataSource() {
        final Map<Object, Object> dataSources = this.createDataSources();
        final RouterDataSource routerDataSource = new RouterDataSource();
        routerDataSource.setTargetDataSources(dataSources);
        routerDataSource.setDefaultTargetDataSource(dataSources.get("main_db"));
        routerDataSource.setLenientFallback(true);
        routerDataSource.afterPropertiesSet();
        System.out.println("In bean: " + routerDataSource.getResolvedDefaultDataSource());
        return routerDataSource;
    }

    @Bean
    @Scope("prototype")
    @Lazy
    public List<DataSourceEntity> getDbsInfo() throws SQLException {
        jdbcTemplate.setDataSource(createDefaultDataSource());
        List<DataSourceEntity> entityList = jdbcTemplate.query("SELECT * FROM databases", (rs, row_number) ->
            new DataSourceEntity(
                    rs.getString("name"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("jdbc_url")
            )
        );
        jdbcTemplate.getDataSource().unwrap(HikariDataSource.class).close();
        return entityList;
    }


    public Map<Object, Object> createDataSources() {
        final Map<Object, Object> result = new HashMap<>();
        result.put("main_db", createDefaultDataSource());
        return result;
    }

    public DataSource createDefaultDataSource() {
        HikariConfig config = new HikariConfig();
        config.setUsername("postgres");
        config.setPassword("internship");
        config.setJdbcUrl("jdbc:postgresql://localhost:3002/main_db");
        return new HikariDataSource(config);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
