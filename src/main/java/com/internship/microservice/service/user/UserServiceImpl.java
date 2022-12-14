package com.internship.microservice.service.user;

import com.internship.microservice.entity.UserEntity;
import com.internship.microservice.exception.GlobalTransactionException;
import com.internship.microservice.mapper.UserMapper;
import com.internship.microservice.service.routing.RoutingService;
import lombok.SneakyThrows;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import javax.transaction.UserTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@EnableAspectJAutoProxy(exposeProxy = true)
public class UserServiceImpl implements UserService {
    @Autowired
    private RoutingService routingService;
    @Autowired
    private UserTransaction userTransaction;
    @Autowired
    private UserMapper userMapper;
    private static final List<UserEntity> dbUsers = new ArrayList<>();
    private static final int BATCH_SIZE = 20;

    @Override
    public void addUsers(List<UserEntity> users) {
        users.forEach((user) -> {
            dbUsers.add(user);
            if (dbUsers.size() == BATCH_SIZE) {
                Map<String, List<UserEntity>> usersByCountry = dbUsers.stream()
                        .collect(Collectors.groupingBy(UserEntity::getNationality));
                ((UserService) AopContext.currentProxy()).insertUsersInGlobalTransaction(usersByCountry);
                usersByCountry.clear();
                dbUsers.clear();
            }
        });
    }

    @SneakyThrows
    public void insertUsersInGlobalTransaction(Map<String, List<UserEntity>> dbUsersToSend) {
        userTransaction.begin();
        try {
            dbUsersToSend.forEach((dbToConnect, users) ->
                routingService.connect(dbToConnect.toLowerCase(), users)
            );
            userTransaction.commit();
        } catch (GlobalTransactionException e) {
            userTransaction.rollback();
            throw new GlobalTransactionException(e.getMessage());
        }
    }

    @Override
    public List<UserEntity> findAll() {
        return userMapper.findAll();
    }

    @Override
    public void truncateUsers() {
        userMapper.truncateUsers();
    }
}
