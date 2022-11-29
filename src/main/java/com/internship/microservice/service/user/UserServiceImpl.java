package com.internship.microservice.service.user;

import com.internship.microservice.entity.UserEntity;
import com.internship.microservice.service.task.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    private final TaskService taskService;
    private static final List<UserEntity> dbAndUsers = new ArrayList<>();
    private static final int BATCH_SIZE = 20;

    @Autowired
    public UserServiceImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void addUsers(List<UserEntity> users) {
            users.forEach((user) -> {
                dbAndUsers.add(user);
                if (dbAndUsers.size() == BATCH_SIZE) {
                    Map<String, List<UserEntity>> usersByCountry = dbAndUsers.stream()
                            .collect(Collectors.groupingBy(UserEntity::getNationality));
                    usersByCountry.keySet()
                            .forEach((dbToConnect) -> taskService
                                    .connect(dbToConnect.toLowerCase(), usersByCountry.get(dbToConnect)));
                    usersByCountry.clear();
                    dbAndUsers.clear();
                }
            });
        }
}
