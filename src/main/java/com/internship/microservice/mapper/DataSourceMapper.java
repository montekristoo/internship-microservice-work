package com.internship.microservice.mapper;

import com.internship.microservice.entity.DataSourceEntity;
import com.internship.microservice.entity.TaskEntity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

@Mapper
public interface DataSourceMapper {

    @Select("SELECT * FROM databases WHERE name = #{name}")
    @Results(id = "dataSourceResult", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "jdbcUrl", column = "jdbc_url"),
            @Result(property = "driverClassName", column = "driver_class_name"),
            @Result(property = "salt", column = "password_salt"),
    })
    DataSourceEntity findByName(@Param("name") String name);

    @ResultMap("dataSourceResult")
    @Options(statementType = StatementType.CALLABLE)
    @Select("SELECT * FROM get_all_databases()")
    List<DataSourceEntity> findAll();


    @Options(statementType = StatementType.CALLABLE)
    @Select("SELECT * FROM current_database()")
    String getCurrentDatabase();

    @Options(statementType = StatementType.CALLABLE)
    @Insert("CALL add_database(#{name}, #{username}, #{password}, #{jdbcUrl}, #{driverClassName}," +
            " #{salt})")
    void addDatabase(DataSourceEntity dataSrc);

    @Delete("DELETE FROM databases WHERE name = #{name}")
    void removeDatabase(@Param("name") String name);

    @Update("UPDATE databases SET name=#{dataSrc.name}, username=#{dataSrc.username}, password=#{dataSrc" +
            ".password}, " +
            "jdbc_url=#{dataSrc.jdbcUrl}, " +
            "driver_class_name = #{dataSrc.driverClassName}," +
            "password_salt = #{dataSrc.salt} " +
            "WHERE id = #{id}")
    void updateDatabase(DataSourceEntity dataSrc, Long id);

    @Insert("INSERT INTO test_table (description) VALUES (#{description})")
    void addTestData(TaskEntity task);
    
    @Select("SELECT datname FROM pg_database")
    List<String> findAllDbNames();

    @Select("SELECT datname FROM pg_database WHERE length(datname) = 2")
    List<String> findCountriesDatabases();

    @Insert("CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, first_name varchar, last_name varchar, genre varchar, " +
            "date_of_birth date, nationality varchar, username varchar, password varchar)")
    void createTableUsers();

    @Delete("DROP TABLE IF EXISTS users")
    void dropTableUsers();
}