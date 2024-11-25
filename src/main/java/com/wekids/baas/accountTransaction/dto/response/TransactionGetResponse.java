package com.wekids.baas.accountTransaction.dto.response;

import com.wekids.baas.accountTransaction.domain.AccountTransaction;
import com.wekids.baas.accountTransaction.domain.enums.AccountTransactionType;
import com.wekids.baas.accountTransaction.domain.enums.CurrencyCode;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class TransactionGetResponse {
    private String title;
    private AccountTransactionType type;
    private Long amount;
    private Long balance;
    private String sender;
    private String receiver;
    private CurrencyCode currencyCode;
    private LocalDateTime transactionDate;

    public static TransactionGetResponse from(AccountTransaction accountTransaction) {
        return TransactionGetResponse.builder()
                .title(accountTransaction.getTitle())
                .type(accountTransaction.getType())
                .amount(accountTransaction.getAmount().longValue())
                .balance(accountTransaction.getBalance().longValue())
                .sender(accountTransaction.getSender())
                .receiver(accountTransaction.getReceiver())
                .currencyCode(accountTransaction.getCurrencyCode())
                .transactionDate(accountTransaction.getTransactionDate())
                .build();
    }

    public static List<TransactionGetResponse> from(List<AccountTransaction> accountTransactions) {
        return accountTransactions.stream()
                .map(accountTransaction -> from(accountTransaction))
                .collect(Collectors.toList());
    }
}
