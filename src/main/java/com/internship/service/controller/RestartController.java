package com.internship.service.controller;

import com.internship.service.service.restart.RestartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RestartController {

    private final RestartServiceImpl restartServiceImpl;

    @Autowired
    public RestartController(RestartServiceImpl restartServiceImpl) {
        this.restartServiceImpl = restartServiceImpl;
    }

    @GetMapping("restart")
    public void restart() {
        restartServiceImpl.restartApp();
        //ServiceApplication.restart();
    }
}
