package com.internship.service.controller;

import com.internship.service.service.restart.RestartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RestartController {

    private final RestartService restartService;

    @Autowired
    public RestartController(RestartService restartService) {
        this.restartService = restartService;
    }

    @GetMapping("restart")
    public void restart() {
        restartService.restartApp();
        //ServiceApplication.restart();
    }
}
