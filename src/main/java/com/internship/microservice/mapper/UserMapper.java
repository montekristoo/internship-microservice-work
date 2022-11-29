package com.internship.microservice.mapper;

import com.internship.microservice.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO users (first_name, last_name, genre, date_of_birth, nationality, username, password) "+
            "VALUES (#{firstName}, #{lastName}, #{genre}, #{dateOfBirth}, #{nationality}, #{username}, #{password})")
    void addUser(UserEntity users);
}
