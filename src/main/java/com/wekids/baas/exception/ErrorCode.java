package com.wekids.baas.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum ErrorCode {
    INVALID_INPUT(BAD_REQUEST, "잘못된 입력 값입니다."),
    BANK_MEMBER_NOT_FOUND(NOT_FOUND, "존재하지 않는 은행 고객입니다."),
    PRODUCT_NOT_FOUND(NOT_FOUND, "존재하지 않는 계좌 상품입니다."),
    ACCOUNT_NOT_FOUND(NOT_FOUND, "존재하지 않는 계좌입니다."),
    BAAS_MEMBER_NOT_FOUND(NOT_FOUND, "존재하지 않는 BaaS 고객입니다."),
    BANK_MEMBER_DUPLICATED(BAD_REQUEST, "이미 존재하는 은행 고객입니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
