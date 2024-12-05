package com.wekids.baas.account.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class AccountStateChangeResponse {
    private LocalDateTime inactiveDate;

    public static AccountStateChangeResponse from(LocalDateTime inactiveDate){
        return new AccountStateChangeResponse(inactiveDate);
    }
}
