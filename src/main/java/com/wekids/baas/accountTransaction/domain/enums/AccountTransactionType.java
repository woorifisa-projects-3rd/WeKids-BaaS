package com.wekids.baas.accountTransaction.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountTransactionType {
    DEPOSIT("입금"),
    WITHDRAW("출금");

    private final String name;
}
