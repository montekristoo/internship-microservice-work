package com.internship.microservice.service.user;

import com.internship.microservice.controller.UserController;
import com.internship.microservice.entity.UserEntity;
import com.internship.microservice.routing.DataSourceContext;
import org.junit.jupiter.api.Test;
import org.mybatis.guice.transactional.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserController userController;
    @Autowired
    private DataSourceContext dataSourceContext;
    @Autowired
    private UserService userService;

    @Test
    public void givenUsersWithDifferentNationality_thenAddThemInGlobalTransactionInSpecificDb() {
        UserServiceImpl.setBatchSize(10);
        List<UserEntity> users = new ArrayList<>();
        UserEntity user = UserEntity.builder()
                .firstName("Mock")
                .lastName("User")
                .genre("F")
                .dateOfBirth(Date.valueOf(("2020-03-01")))
                .nationality("MD")
                .username("mockuu")
                .password("password")
                .build();
        for (int i = 0; i < 6; i++) {
            users.add(user);
        }
        user.setNationality("UK");
        for (int i = 0; i < 6; i++) {
            users.add(user);
        }
        userService.addUsers(users);
    }

}
