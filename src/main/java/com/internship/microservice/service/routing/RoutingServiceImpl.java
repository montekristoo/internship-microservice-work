package com.internship.microservice.service.routing;

import com.internship.microservice.entity.UserEntity;
import com.internship.microservice.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RoutingServiceImpl implements RoutingService {
    private final UserMapper userMapper;

    public RoutingServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void connect(String name, List<UserEntity> users) {
        userMapper.addUsers(users);
    }
}
