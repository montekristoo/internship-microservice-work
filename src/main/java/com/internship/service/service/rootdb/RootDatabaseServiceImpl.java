package com.internship.service.service.rootdb;

import com.internship.service.annotations.SetDatabase;
import com.internship.service.entity.DataSourceEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
@EnableCaching
@Slf4j
public class RootDatabaseServiceImpl implements RootDatabaseService {

    private final JdbcTemplate jdbcTemplate;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final DataSource defaultDataSource;
    private final CacheManager cacheManager;

    public RootDatabaseServiceImpl(JdbcTemplate jdbcTemplate, @Qualifier("defaultDataSource") DataSource defaultDataSource, CacheManager cacheManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.defaultDataSource = defaultDataSource;
        this.cacheManager = cacheManager;
    }

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
    @Cacheable(value = "dataSourceEntities")
    public List<DataSourceEntity> findAll() {
        System.out.println("Query to DB");
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
    public DataSourceEntity findByName(String name) {
        return jdbcTemplate.queryForObject("SELECT * FROM databases WHERE name=?", new BeanPropertyRowMapper<>(DataSourceEntity.class), name);
    }

    @Override
    public List<DataSourceEntity> getCachedDb() {
        Cache cache = cacheManager.getCache("dataSourceEntities");
        List<DataSourceEntity> dataSourceEntities = (List<DataSourceEntity>) cache.get(SimpleKey.EMPTY).get();
        return dataSourceEntities;
    }
}
