package com.wekids.baas.account.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class MemberAccountGetResponse {
    private String accountNumber;
    private String bankName;
    private BigDecimal balance;
    private String state;
    private String bankMemberName;
    private String productName;
    private String productType;
}
