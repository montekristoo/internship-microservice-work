package com.internship.microservice.service.user;

import com.internship.microservice.entity.UserEntity;
import com.internship.microservice.service.routing.RoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RoutingService routingService;
    private static final List<UserEntity> dbAndUsers = new ArrayList<>();
    private static final int BATCH_SIZE = 20;


    @Override
    public void addUsers(List<UserEntity> users) {
        users.forEach((user) -> {
            dbAndUsers.add(user);
            if (dbAndUsers.size() == BATCH_SIZE) {
                Map<String, List<UserEntity>> usersByCountry = dbAndUsers.stream()
                        .collect(Collectors.groupingBy(UserEntity::getNationality));
                sendToTransactionContainer(usersByCountry);
                usersByCountry.clear();
                dbAndUsers.clear();
            }
        });
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendToTransactionContainer(Map<String, List<UserEntity>> dbUsersToSend) {
            try {
                System.out.println(TransactionSynchronizationManager.getCurrentTransactionName());
                dbUsersToSend.keySet()
                        .forEach((dbToConnect) -> {
                            System.out.println(dbToConnect);
                            routingService.connect(dbToConnect.toLowerCase(), dbUsersToSend.get(dbToConnect));
                        });
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }
}
