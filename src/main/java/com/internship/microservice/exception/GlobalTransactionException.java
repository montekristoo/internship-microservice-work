package com.internship.microservice.exception;

public class GlobalTransactionException extends RuntimeException {
    public GlobalTransactionException(String message) {
        super(message);
    }
}
