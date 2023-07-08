package com.example.GameApp.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    NAME_NOT_FOUND_ERROR_CODE("1000"),
    BAD_REQUEST_ERROR_CODE("1001"),
    NOT_FOUND_ERROR_CODE("1002"),
    METHOD_NOT_ALLOWED_ERROR_CODE("1003"),
    INTERNAL_SERVER_ERROR_CODE("1004");


    private String errorCode;

    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getValue() {
        return errorCode;
    }
}
