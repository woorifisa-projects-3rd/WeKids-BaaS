package com.wekids.baas.accountTransaction.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TransactionGetResponse {
    private String title;
    private String type;
    private Long amount;
    private Long balance;
    private String sender;
    private String receiver;
    private String currencyCode;
    private LocalDateTime transactionDate;
}
