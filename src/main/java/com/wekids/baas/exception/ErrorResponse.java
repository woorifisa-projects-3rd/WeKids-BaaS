package com.wekids.baas.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private int status;
    private String message;
    private String details;
    private LocalDateTime timestamp;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ValidErrorResponse> errors;

    public static ErrorResponse of(ErrorCode errorCode, String details) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus().value())
                .message(errorCode.getMessage())
                .details(details)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ErrorResponse of(List<FieldError> errors, ErrorCode errorCode) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus().value())
                .message(errorCode.getMessage())
                .timestamp(LocalDateTime.now())
                .errors(errors.stream().map(ValidErrorResponse::from).toList())
                .build();
    }
}