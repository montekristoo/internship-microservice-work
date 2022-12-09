package com.internship.microservice.service.user;

import com.internship.microservice.entity.UserEntity;

import java.util.List;

public interface UserService {
    void addUsers(List<UserEntity> users);
    void connect(String name, List<UserEntity> users);
}
