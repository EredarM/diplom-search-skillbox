package com.skillbox.searchengine.controller;

import com.skillbox.searchengine.exception.NotFoundPageException;
import com.skillbox.searchengine.exception.ProcessIndexingException;
import com.skillbox.searchengine.exception.StartProccesIndexingException;
import com.skillbox.searchengine.model.response.ErrorDescription;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    private static final String PAGE_NOT_FOUND = "page-not-found";
    private static final String PROCESS_INDEXING = "process-indexing";
    private static final String START_PROCESS_INDEXING = "start-process-indexing";

    @ExceptionHandler(NotFoundPageException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDescription pageNotFoundException(NotFoundPageException exception) {
        return new ErrorDescription(
                exception.getMessage(),
                PAGE_NOT_FOUND
        );
    }

    @ExceptionHandler(ProcessIndexingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDescription processIndexingException(ProcessIndexingException exception) {
        return new ErrorDescription(
                exception.getMessage(),
                PROCESS_INDEXING
        );
    }

    @ExceptionHandler(StartProccesIndexingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDescription startProcessIndexingException(StartProccesIndexingException exception) {
        return new ErrorDescription(
                exception.getMessage(),
                START_PROCESS_INDEXING
        );
    }
}
