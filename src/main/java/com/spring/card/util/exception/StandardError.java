package com.spring.card.util.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardError {
    private int statusCode;

    private String error;

    private String message;

    private String path;

    public StandardError(int statusCode, String error, String message, String path) {
        this.statusCode = statusCode;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
