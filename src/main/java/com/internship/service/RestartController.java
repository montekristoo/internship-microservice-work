package com.internship.service;

import com.internship.service.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RestartController {

    @Autowired
    private TaskService taskService;

    @GetMapping("restart")
    public void restartApplication() {
        ServiceApplication.restart();
        System.out.println(taskService.getAll().size());
    }
}
