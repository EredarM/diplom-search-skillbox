package com.skillbox.searchengine.model.response;

public class ErrorDescription {
    private final String message;
    private final String code;

    public ErrorDescription(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
