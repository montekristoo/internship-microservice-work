package com.internship.service.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {DatabaseNotFoundException.class})
    protected ResponseEntity<Object> handleDbNotFoundException (RuntimeException e, WebRequest webRequest) {
       String body = "This database doesn't exists";
       return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

}
