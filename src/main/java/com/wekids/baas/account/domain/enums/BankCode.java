package com.wekids.baas.account.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BankCode {
    WOORI_BANK("020", "우리은행"),
    KOOKMIN_BANK("004", "국민은행"),
    HANA_BANK("081", "하나은행"),
    SHINHAN_BANK("088", "신한은행"),

    KAKAO_BANK("090","카카오뱅크");


    private final String code;
    private final String name;
}
