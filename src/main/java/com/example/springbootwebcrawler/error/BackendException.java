package com.example.springbootwebcrawler.error;

public class BackendException extends RuntimeException{

    public BackendException(String message, Throwable cause) {
        super(message, cause);
    }
}
