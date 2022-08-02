package com.skillbox.searchengine.exception;

public abstract class AbstractException extends RuntimeException{
    public AbstractException(String message) {
        super(message);
    }
}
