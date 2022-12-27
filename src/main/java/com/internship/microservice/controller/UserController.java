package com.internship.microservice.controller;

import com.internship.microservice.entity.UserEntity;
import com.internship.microservice.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("users")
    public void addUsers(@RequestBody List<@Valid UserEntity> users) {
       userService.addUsers(users);
    }

    @PostMapping("user")
    public void addUser(@RequestBody @Valid UserEntity user) {
        userService.addUsers(Collections.singletonList(user));
    }
}
