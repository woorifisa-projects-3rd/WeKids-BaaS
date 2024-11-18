package com.wekids.baas.exception;

import lombok.Getter;

@Getter
public class BaasException extends RuntimeException {

    private final ErrorCode errorCode;

    public BaasException(final ErrorCode errorCode, String details) {
        super(details);
        this.errorCode = errorCode;
    }
}
