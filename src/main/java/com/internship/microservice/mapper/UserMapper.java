package com.internship.microservice.mapper;

import com.internship.microservice.entity.UserEntity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.executor.BatchResult;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO users (first_name, last_name, genre, date_of_birth, nationality, username, password) "+
            "VALUES (#{firstName}, #{lastName}, #{genre}, #{dateOfBirth}, #{nationality}, #{username}, #{password})")
    void addUser(UserEntity user);
    @Flush
    List<BatchResult> flush();
    @Select("SELECT * FROM users")
    List<UserEntity> findAll();
    @Delete("TRUNCATE TABLE users")
    void truncateUsers();
}
