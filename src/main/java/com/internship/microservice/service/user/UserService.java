package com.internship.microservice.service.user;

import com.internship.microservice.entity.UserEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    void addUsers(List<UserEntity> users);
    void sendToTransactionContainer(Map<String, List<UserEntity>> map);
}
