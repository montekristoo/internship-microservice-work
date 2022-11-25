package com.internship.service.mapper;

import com.internship.service.entity.DataSourceEntity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

@Mapper
public interface MainDatabaseMapper {

    @Select("SELECT * FROM databases WHERE name = #{name}")
    @Results(id = "dataSourceResult", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "jdbcUrl", column = "jdbc_url"),
            @Result(property = "driverClassName", column = "driver_class_name")
    })
    DataSourceEntity findByName(@Param("name") String name);
    @ResultMap("dataSourceResult")
    @Options(statementType = StatementType.CALLABLE)
    @Select("SELECT * FROM get_all_datasources()")
    List<DataSourceEntity> findAll();

    @ResultMap("dataSourceResult")
    @Options(statementType = StatementType.CALLABLE)
    @Select("SELECT * FROM current_database()")
    String getCurrentDatabase();

    @Insert("INSERT INTO databases (name, username, password, jdbc_url, driver_class_name)" +
            "VALUES (#{name}, #{username}, #{password}, #{jdbcUrl}, #{driverClassName})")
    void addDatabase(DataSourceEntity dataSrc);

    @Delete("DELETE FROM databases WHERE name = #{name}")
    void removeDatabase(@Param("name") String name);
}
