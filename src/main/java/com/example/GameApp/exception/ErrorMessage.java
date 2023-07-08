package com.example.GameApp.exception;

public enum ErrorMessage {
    NAME_NOT_FOUND_MESSAGE("Name Not Found"),
    BAD_REQUEST_MESSAGE("Bad Request"),
    NOT_FOUND_MESSAGE("Not Found"),
    METHOD_NOT_ALLOWED_MESSAGE("Method Not Allowed"),
    INTERNAL_SERVER_ERROR_MESSAGE("Something Went Wrong"),
    GAME_NAME_INVALID_MESSAGE("Name can't be blank or null"),
    REQUEST_INVALID_MESSAGE("Request can't be blank or null"),
    CREATION_DATE_INVALID_MESSAGE("CreationDate can't be blank, null or not in proper format MM/dd/yyyy HH:mm:ss");

    private String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getValue() {
        return errorMessage;
    }

}
