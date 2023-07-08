package com.example.GameApp.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {

    private String errorCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    @JsonInclude(JsonInclude.Include. NON_NULL)
    private List<String> errors;

    private ErrorResponse() {
        timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String message, String errorCode) {
        this();
        this.message = message;
        this.errorCode = errorCode;
    }

    public ErrorResponse(String message, Throwable ex) {
        this();
        this.message = message;
    }

    public ErrorResponse(String message, String errorCode, List<String> errors) {
        this();
        this.message = message;
        this.errorCode = errorCode;
        this.errors = errors;
    }

}