package com.internship.microservice.service.user;

import com.internship.microservice.controller.UserController;
import com.internship.microservice.entity.UserEntity;
import com.internship.microservice.routing.DataSourceContext;
import com.internship.microservice.util.PasswordUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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

    @AfterEach
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
            usersFromMd.add(getUser(false, "md"));
        }
        for (int i = 0; i < 10; i++) {
            usersFromUk.add(getUser(false, "uk"));
        }
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
            usersFromMd.add(getUser(true, "md"));
        }
        for (int i = 0; i < 10; i++) {
            usersFromUk.add(getUser(false, "uk"));
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
            users.add(getUser(false, "md"));
        }
        users.get(4).setGenre("Long genre name");
        Assertions.assertThrows(
                ConstraintViolationException.class, () -> userController.addUsers(users));
    }

    public static UserEntity getUser(boolean samePassword, String country) {
        return UserEntity.builder()
                .firstName("Mock")
                .lastName("User")
                .genre("F")
                .dateOfBirth(Date.valueOf(("2020-03-01")))
                .nationality(country.toUpperCase())
                .username("mock_user")
                .password(samePassword ? "same" : PasswordUtils.generateRandomString())
                .build();
    }

}
