package com.wekids.baas.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {
    INVALID_INPUT(BAD_REQUEST, "잘못된 입력 값입니다."),
    BANK_MEMBER_NOT_FOUND(NOT_FOUND, "존재하지 않는 은행 고객입니다."),
    PRODUCT_NOT_FOUND(NOT_FOUND, "존재하지 않는 계좌 상품입니다."),
    ACCOUNT_NOT_FOUND(NOT_FOUND, "존재하지 않는 계좌입니다."),
    BAAS_MEMBER_NOT_FOUND(NOT_FOUND, "존재하지 않는 BaaS 고객입니다."),
    BANK_MEMBER_DUPLICATED(BAD_REQUEST, "이미 존재하는 은행 고객입니다."),
    INACTIVE_ACCOUNT(BAD_REQUEST, "비활성 계좌입니다."),
    INSUFFICIENT_BALANCE(BAD_REQUEST, "잔액이 부족합니다."),
    SENDER_AND_RECEIVER_SAME(BAD_REQUEST, "보내는 분과 받는 분이 같습니다."),
    START_IS_AFTER_END(BAD_REQUEST, "시작 날짜가 끝 날짜보다 과거여야 합니다."),
    INVALID_PRODUCT(BAD_REQUEST, "유효하지 않은 계좌 상품입니다."),
    INACTIVE_BANK_MEMBER(BAD_REQUEST, "비활성 고객입니다."),
    REGISTRATION_NOT_FOUND(NOT_FOUND, "등록된 서비스가 아닙니다."),
    CARD_NOT_FOUNT(NOT_FOUND, "존재하지 않은 카드입니다."),
    CARD_EXPIRE(INTERNAL_SERVER_ERROR, "만료된 카드입니다."),
    INCORRECT_PASSWORD(INTERNAL_SERVER_ERROR, "비밀번호가 일치 하지 않습니다."),
    REGISTRATION_DUPLICATED(BAD_REQUEST, "이미 등록된 서비스입니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
