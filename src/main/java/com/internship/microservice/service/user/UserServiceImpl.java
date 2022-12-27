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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@EnableAspectJAutoProxy(exposeProxy = true)
public class UserServiceImpl implements UserService {
    private final RoutingService routingService;
    private final UserTransaction userTransaction;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(RoutingService routingService, UserTransaction userTransaction, UserMapper userMapper) {
        this.routingService = routingService;
        this.userTransaction = userTransaction;
        this.userMapper = userMapper;
    }

    @Override
    public void addUsers(List<UserEntity> users) {
        ((UserService) AopContext.currentProxy())
                .insertUsersInGlobalTransaction(prepareUsers(users));
    }

    private Map<String, List<UserEntity>> prepareUsers(List<UserEntity> users) {
        return users.stream()
                .collect(Collectors.groupingBy(UserEntity::getNationality));
    }

    @SneakyThrows
    public void insertUsersInGlobalTransaction(Map<String, List<UserEntity>> dbUsersToSend) {
        userTransaction.begin();
        try {
            dbUsersToSend.forEach((dbToConnect, users) ->
                    routingService.connect(dbToConnect.toLowerCase(), users)
            );
            userTransaction.commit();
        } catch (RuntimeException e) {
            userTransaction.rollback();
            System.out.println(e.getMessage());
            throw new GlobalTransactionException("Transaction failed");
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
