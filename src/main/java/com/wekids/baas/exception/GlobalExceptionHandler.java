package com.wekids.baas.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.wekids.baas.exception.ErrorCode.INVALID_INPUT;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaasException.class)
    public ResponseEntity<ErrorResponse> handleWeKidsException(BaasException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, exception.getMessage());
        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidException(
            MethodArgumentNotValidException exception) {
        return ResponseEntity.status(INVALID_INPUT.getStatus())
                .body(ErrorResponse.of(exception.getBindingResult().getFieldErrors(), INVALID_INPUT));
    }
}