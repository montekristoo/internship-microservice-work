package com.internship.service.dbConfig;


import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;



@EnableScheduling
@Service
public class TedService {

    @Scheduled(fixedDelayString = "${spring.task.delay}")
    public void scheduleFixedDelayTask() {
        System.out.println("Fixed delay task - " + System.currentTimeMillis() / 1000);
    }
}
