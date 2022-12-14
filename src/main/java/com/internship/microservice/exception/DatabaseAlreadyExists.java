package com.internship.microservice.exception;

public class DatabaseAlreadyExists extends RuntimeException {

    public DatabaseAlreadyExists(String message) {
        super("Database " + message + " already exists");
    }
}
