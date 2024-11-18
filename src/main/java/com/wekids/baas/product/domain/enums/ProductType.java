package com.wekids.baas.product.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductType {
    CHECKING("입출금"),
    FREE_SAVINGS("자유적금"),
    FIXED_SAVINGS("정기적금"),
    DEPOSIT("예금");

    private final String name;

}
