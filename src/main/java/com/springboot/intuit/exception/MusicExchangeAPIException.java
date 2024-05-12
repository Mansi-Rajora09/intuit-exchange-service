package com.springboot.intuit.exception;

import org.springframework.http.HttpStatus;

public class MusicExchangeAPIException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public MusicExchangeAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public MusicExchangeAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
