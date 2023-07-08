package com.example.GameApp.exception;

public class NameNotFoundException extends RuntimeException {

    private ErrorResponse errorResponse;

    public NameNotFoundException(ErrorResponse errorResponse) {
        super();
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}