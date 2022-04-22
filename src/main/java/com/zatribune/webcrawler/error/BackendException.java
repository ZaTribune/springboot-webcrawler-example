package com.zatribune.webcrawler.error;

public class BackendException extends RuntimeException{

    public BackendException(String message, Throwable cause) {
        super(message, cause);
    }
}
