package com.internship.microservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Transaction failed", value = HttpStatus.INTERNAL_SERVER_ERROR)
public class GlobalTransactionException extends RuntimeException {
    public GlobalTransactionException(String message) {
        super(message);
    }
}
