package com.wekids.baas.accountTransaction.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CurrencyCode {
    KRW("원"),
    USD("달러"),
    EUR("유로"),
    JPY("엔"),
    GBP("파운드"),
    CNY("위안");

    private final String unit;

}
