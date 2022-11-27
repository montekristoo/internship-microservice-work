package com.internship.microservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Configuration
public class DatabaseCredentialsConfig {

    @Bean
    @Scope("prototype")
    public Map<String, String> clientPasswords() {
        Map<String, String> credentialsMap = new HashMap<>();
        Scanner scanner;
        try {
            scanner = new Scanner(new File("passwords.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while(scanner.hasNext()) {
            credentialsMap.put(scanner.next(), scanner.next());
        }
        return credentialsMap;
    }
}
