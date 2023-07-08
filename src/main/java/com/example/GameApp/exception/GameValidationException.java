package com.example.GameApp.exception;

public class GameValidationException extends RuntimeException {

    private ErrorResponse errorResponse;

    public GameValidationException(ErrorResponse errorResponse) {
        super();
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}