package com.wekids.baas.account.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AccountCreateResponse {
    String accountNumber;
    String type;
    LocalDateTime expireDate;

    public static AccountCreateResponse of(String accountNumber, String type, LocalDateTime expireDate) {
        return AccountCreateResponse.builder()
                .accountNumber(accountNumber)
                .type(type)
                .expireDate(expireDate)
                .build();
    }
}
