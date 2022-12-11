package com.internship.microservice.service.routing;

import com.internship.microservice.entity.UserEntity;
import com.internship.microservice.mapper.UserMapper;
import com.internship.microservice.routing.RoutingDataSource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Slf4j
@Service
public class RoutingServiceImpl implements RoutingService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoutingDataSource routingDataSource;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Override
    @SneakyThrows
    public void connect(String name, List<UserEntity> users) {
            for (UserEntity user : users) {
                System.out.println("add");
                userMapper.addUser(user);
            }
    }
}
