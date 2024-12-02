package com.wekids.baas.accountTransaction.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.wekids.baas.exception.BaasException;
import com.wekids.baas.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum AccountTransactionRequestType {
    ALL,
    DEPOSIT,
    WITHDRAWAL;

    @JsonCreator
    public static AccountTransactionRequestType parsing(String value) {
        return Stream.of(AccountTransactionRequestType.values())
                .filter(type -> type.toString().equals(value.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new BaasException(ErrorCode.INVALID_INPUT, "거래 타입: "  + value));
    }
}