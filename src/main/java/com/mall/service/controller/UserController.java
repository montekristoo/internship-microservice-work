package com.mall.service.controller;

import com.mall.service.entity.UserEntity;
import com.mall.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("users")
    void addUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

}
