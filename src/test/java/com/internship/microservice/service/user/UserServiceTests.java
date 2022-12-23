package com.internship.microservice.service.user;

import com.internship.microservice.controller.UserController;
import com.internship.microservice.entity.UserEntity;
import com.internship.microservice.routing.DataSourceContext;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private DataSourceContext dataSourceContext;
    @Autowired
    private UserService userService;
    @Autowired
    private UserController userController;

    @BeforeEach
    public void reset() {
        dataSourceContext.setContext("md");
        userService.truncateUsers();
        dataSourceContext.removeContext();
        dataSourceContext.setContext("uk");
        userService.truncateUsers();
        dataSourceContext.removeContext();
    }

    @SneakyThrows
    @Test
    public void givenUsersWithDifferentNationality_thenAddThemInGlobalTransactionInSpecificDb() {
        List<UserEntity> usersFromMd = new ArrayList<>();
        List<UserEntity> usersFromUk = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            usersFromMd.add(getMdUser(String.valueOf(i), false));
        }
        for (int i = 0; i < 10; i++) {
            usersFromUk.add(getUkUser(String.valueOf(i), false));
        }
        System.out.println(usersFromMd + " " + usersFromUk);
        userService.addUsers(usersFromMd);
        userService.addUsers(usersFromUk);
        dataSourceContext.setContext("md");
        Assertions.assertEquals(userService.findAll().size(), 10);
        dataSourceContext.removeContext();
        dataSourceContext.setContext("uk");
        Assertions.assertEquals(userService.findAll().size(), 10);
        dataSourceContext.removeContext();
    }

    @Test
    @SneakyThrows
    public void givenUsersWithDifferentNationality_thenRollbackGlobalTransactionOnError() {
        List<UserEntity> usersFromMd = new ArrayList<>();
        List<UserEntity> usersFromUk = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            usersFromMd.add(getMdUser(String.valueOf(i), true));
        }
        for (int i = 0; i < 10; i++) {
            usersFromUk.add(getUkUser(String.valueOf(i), false));
        }
        // action that causes the error and rollback global transaction
        userService.addUsers(usersFromMd);
        try {
            userService.addUsers(usersFromUk);
        } catch (RuntimeException r) {
            System.out.println(r.getMessage());
        }
        dataSourceContext.setContext("md");
        Assertions.assertEquals(userService.findAll().size(), 0);
        dataSourceContext.removeContext();
        dataSourceContext.setContext("uk");
        Assertions.assertEquals(userService.findAll().size(), 0);
        dataSourceContext.removeContext();
    }

    @Test
    public void givenListWithUsersWithConstraintValidationError_thenThrowException() {
        List<UserEntity> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(getMdUser(String.valueOf(i), false));
        }
        users.get(4).setGenre("Long genre name");
        Assertions.assertThrows(
                ConstraintViolationException.class, () -> userController.addUsers(users));
    }

    public static UserEntity getMdUser(String password, boolean samePassword) {
        return UserEntity.builder()
                .firstName("Mock")
                .lastName("User")
                .genre("F")
                .dateOfBirth(Date.valueOf(("2020-03-01")))
                .nationality("MD")
                .username("mock_user")
                .password(samePassword ? "same" : password)
                .build();
    }

    public static UserEntity getUkUser(String password, boolean samePassword) {
        return UserEntity.builder()
                .firstName("Mock")
                .lastName("User")
                .genre("F")
                .dateOfBirth(Date.valueOf(("2020-03-01")))
                .nationality("UK")
                .username("mock_user")
                .password(samePassword ? "same" : password)
                .build();
    }

}
