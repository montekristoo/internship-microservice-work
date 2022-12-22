package com.internship.microservice.mapper;

import com.internship.microservice.entity.UserEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert({
            "<script>" +
                    "INSERT INTO users (first_name, last_name, genre, date_of_birth, nationality, username, password) " +
                    "VALUES " +
                    "<foreach item='item' index='index' collection='list' separator=','>" +
                    "(#{item.firstName}, #{item.lastName}, #{item.genre}, #{item.dateOfBirth}, #{item.nationality}, #{item.username}, #{item.password})" +
                    "</foreach>" +
                    "</script>"
    })
    void addUsers(List<UserEntity> user);

    @Select("SELECT * FROM users")
    List<UserEntity> findAll();

    @Delete("TRUNCATE TABLE users")
    void truncateUsers();
}
