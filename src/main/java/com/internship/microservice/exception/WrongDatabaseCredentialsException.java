package com.internship.microservice.exception;

public class WrongDatabaseCredentialsException extends RuntimeException{
    public WrongDatabaseCredentialsException(String message) {
        super("Wrong credentials for " + message + " database");
    }
}
