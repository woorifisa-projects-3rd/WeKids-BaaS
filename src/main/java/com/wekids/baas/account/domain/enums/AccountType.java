package com.wekids.baas.account.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AccountType {
    CHECKING("입출금"),
    FREE_SAVINGS("자유적금"),
    FIXED_SAVINGS("정기적금"),
    DEPOSIT("예금");

    private final String name;

}
