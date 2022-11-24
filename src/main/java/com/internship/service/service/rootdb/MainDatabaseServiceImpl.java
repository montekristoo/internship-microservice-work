package com.internship.service.service.rootdb;

import com.internship.service.annotations.SetDatabase;
import com.internship.service.entity.DataSourceEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
@EnableCaching
@Slf4j
@PropertySource(value = "classpath:security.properties")
public class MainDatabaseServiceImpl implements MainDatabaseService {

    @Value("${databases.password}")
    private String password;
    private final JdbcTemplate jdbcTemplate;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final DataSource defaultDataSource;
    private final CacheManager cacheManager;
    private static final String addDb_SQL = "call add_datasource(?, ?, ?, ?, ?)";
    private static final String removeDb_SQL = "DELETE FROM databases WHERE name=?";
    private static final String findAllDb_SQL = "SELECT * FROM get_all_datasources()";
    private static final String findDatabaseByName_SQL = "SELECT * FROM databases WHERE name=?";
    private static final String getCurrentDb_SQL = "SELECT * FROM current_database()";

    public MainDatabaseServiceImpl(JdbcTemplate jdbcTemplate, @Qualifier("defaultDataSource") DataSource defaultDataSource, CacheManager cacheManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.defaultDataSource = defaultDataSource;
        this.cacheManager = cacheManager;
    }

    @Override
    @SetDatabase(value = "main_db")
    @CacheEvict(value = "dataSourceEntities", allEntries = true)
    public void addDataSource(DataSourceEntity dataSourceEntity) {
        dataSourceEntity.setPassword(passwordEncoder.encode(dataSourceEntity.getPassword()));
        jdbcTemplate.update(addDb_SQL, dataSourceEntity.getName(), dataSourceEntity.getUsername(),
                dataSourceEntity.getPassword(), dataSourceEntity.getJdbcUrl(), dataSourceEntity.getDriverClassName());
    }

    @Override
    @SetDatabase(value = "main_db")
    @CacheEvict(value = "dataSourceEntities", allEntries = true)
    public void removeDataSource(String name) {
        jdbcTemplate.update(removeDb_SQL, name);
    }

    @Override
    public List<DataSourceEntity> findAll() {
        log.info("Query from DB");
        jdbcTemplate.setDataSource(defaultDataSource);
        List<DataSourceEntity> dataSourceEntities = jdbcTemplate.query(findAllDb_SQL, (rs, row_number) ->
                new DataSourceEntity(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        password,
                        rs.getString("jdbc_url"),
                        rs.getString("driver_class_name")
                )
        );
        log.info(String.valueOf(dataSourceEntities.hashCode()));
        return dataSourceEntities;
    }

    @Override
    @Cacheable(value = "dataSourceEntities", key = "#name")
    public DataSourceEntity findByName(String name) {
        jdbcTemplate.setDataSource(defaultDataSource);
        DataSourceEntity dataSource = jdbcTemplate.queryForObject(findDatabaseByName_SQL, new BeanPropertyRowMapper<>(DataSourceEntity.class), name);
        dataSource.setPassword(password);
        return dataSource;
    }


    @Override
    public DataSourceEntity getCachedDb(String name) {
        Cache cache = cacheManager.getCache("dataSourceEntities");
        return cache.get(name, DataSourceEntity.class);
    }

    @Override
    public String getCurrentDb() {
        return jdbcTemplate.queryForObject(getCurrentDb_SQL, String.class);
    }
}
