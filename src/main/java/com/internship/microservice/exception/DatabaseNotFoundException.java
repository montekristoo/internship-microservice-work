package com.internship.microservice.exception;

public class DatabaseNotFoundException extends RuntimeException {

    public DatabaseNotFoundException(String message) {
        super("Database " + message + " not found");
    }
}
