package com.internship.microservice.service.user;

import com.internship.microservice.entity.UserEntity;
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
    private static final List<UserEntity> dbUsers = new ArrayList<>();
    private static final int BATCH_SIZE = 20;

    @Override
    public void addUsers(List<UserEntity> users) {
        users.forEach((user) -> {
            dbUsers.add(user);
            if (dbUsers.size() == BATCH_SIZE) {
                Map<String, List<UserEntity>> usersByCountry = dbUsers.stream()
                        .collect(Collectors.groupingBy(UserEntity::getNationality));
                ((UserService)AopContext.currentProxy()).sendToTransactionContainer(usersByCountry);
                usersByCountry.clear();
                dbUsers.clear();
            }
        });
    }

    @SneakyThrows
    public void sendToTransactionContainer(Map<String, List<UserEntity>> dbUsersToSend) {
        userTransaction.begin();
        routingService.connect("md", dbUsersToSend.get("MD"));
        routingService.connect("uk", dbUsersToSend.get("UK"));
//            dbUsersToSend.keySet()
//                    .forEach((dbToConnect) -> {
//                        this.routingService.connect(dbToConnect.toLowerCase(), dbUsersToSend.get(dbToConnect));
//                    });
        userTransaction.commit();
    }
}
