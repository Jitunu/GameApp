package com.example.GameApp.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;

import static com.example.GameApp.exception.ErrorCode.*;
import static com.example.GameApp.exception.ErrorMessage.*;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandlers extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NameNotFoundException ex) {
        return new ResponseEntity<>(ex.getErrorResponse(),NOT_FOUND);
    }

    @ExceptionHandler(ValidationException .class)
    public ResponseEntity<ErrorResponse> handleGameValidationException(ValidationException  ex) {

        return new ResponseEntity<>(((GameValidationException)ex.getCause()).getErrorResponse(),BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(METHOD_NOT_ALLOWED_MESSAGE.getValue(), METHOD_NOT_ALLOWED_ERROR_CODE.getValue());
        return new ResponseEntity<>(errorResponse, METHOD_NOT_ALLOWED);
    }

    @Override
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND_MESSAGE.getValue(), NOT_FOUND_ERROR_CODE.getValue());
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(INTERNAL_SERVER_ERROR_MESSAGE.getValue(), INTERNAL_SERVER_ERROR_CODE.getValue());
        return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
    }
}