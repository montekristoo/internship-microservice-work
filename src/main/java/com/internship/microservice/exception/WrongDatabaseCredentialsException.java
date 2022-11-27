package com.internship.microservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WrongDatabaseCredentialsException extends RuntimeException{

    public WrongDatabaseCredentialsException() {
    }

    public WrongDatabaseCredentialsException(String message) {
        super("Wrong credentials for " + message + " database");
    }
}
