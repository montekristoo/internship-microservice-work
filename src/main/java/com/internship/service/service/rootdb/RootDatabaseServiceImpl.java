package com.internship.service.service.rootdb;

import com.hazelcast.config.Config;
import com.internship.service.annotations.SetDatabase;
import com.internship.service.entity.DataSourceEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@EnableCaching
@Slf4j
public class RootDatabaseServiceImpl implements RootDatabaseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    @Qualifier("defaultDataSource")
    private DataSource defaultDataSource;
    @Autowired
    private CacheManager cacheManager;

    @Override
    @SetDatabase(value = "main_db")
    @CacheEvict(value = "dataSourceEntities", allEntries = true)
    public void addDataSource(DataSourceEntity dataSourceEntity) {
        dataSourceEntity.setPassword(passwordEncoder.encode(dataSourceEntity.getPassword()));
        jdbcTemplate.update("call add_datasource(?, ?, ?, ?, ?)", dataSourceEntity.getName(), dataSourceEntity.getUsername(),
                dataSourceEntity.getPassword(), dataSourceEntity.getJdbcUrl(), dataSourceEntity.getDriverClassName());
    }

    @Override
    @SetDatabase(value = "main_db")
    @CacheEvict(value = "dataSourceEntities", allEntries = true)
    public void removeDataSource(String name) {
        jdbcTemplate.update("DELETE FROM databases WHERE name=?", name);
    }

    @Override
    public List<DataSourceEntity> findAll() {
             jdbcTemplate.setDataSource(defaultDataSource);
            List<DataSourceEntity> dataSourceEntities = jdbcTemplate.query("SELECT * FROM databases", (rs, row_number) ->
                    new DataSourceEntity(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("jdbc_url"),
                            rs.getString("driver_class_name")
                    )
            );
            log.info(String.valueOf(dataSourceEntities.hashCode()));
            return dataSourceEntities;
        }

    @Override
    @Cacheable(value = "dataSourceEntities", key = "#name")
    public List<DataSourceEntity> findByUser(String name) {
        List<DataSourceEntity> dataSourceEntities = jdbcTemplate.query("SELECT * FROM databases WHERE username='postgres'", (rs, row_number) ->
                new DataSourceEntity(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("jdbc_url"),
                        rs.getString("driver_class_name")
                )
        );
        return dataSourceEntities;
    }

    @Override
    public DataSourceEntity getCachedDb() {
        DataSourceEntity dataSource =  Objects.requireNonNull(cacheManager
                        .getCache("dataSourceEntities"))
                .get("#name", DataSourceEntity.class);
        System.out.println(cacheManager.getCacheNames());
        return dataSource;
    }
}
