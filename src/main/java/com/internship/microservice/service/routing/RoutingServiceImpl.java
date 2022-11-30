package com.internship.microservice.service.routing;

import com.internship.microservice.entity.UserEntity;
import com.internship.microservice.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Slf4j
@Service
@EnableScheduling
public class RoutingServiceImpl implements RoutingService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void connect(String name, List<UserEntity> users) {
        if (name.equals("md")) {
            int i = 1/0;
        }
        System.out.println(TransactionSynchronizationManager.getCurrentTransactionName());
        System.out.println(users);
        users.forEach(userMapper::addUser);
    }
}
