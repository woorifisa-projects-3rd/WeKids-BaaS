package com.wekids.baas.support.fixture;

import com.wekids.baas.account.domain.Account;
import com.wekids.baas.accountTransaction.domain.AccountTransaction;
import com.wekids.baas.accountTransaction.domain.enums.AccountTransactionType;
import com.wekids.baas.accountTransaction.domain.enums.CurrencyCode;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public class AccountTransactionFixture {
    @Builder.Default
    private Long id = 1L;
    @Builder.Default
    private String title = "문방구";
    @Builder.Default
    private AccountTransactionType type = AccountTransactionType.WITHDRAWAL;
    @Builder.Default
    private BigDecimal amount = BigDecimal.valueOf(2000);
    @Builder.Default
    private BigDecimal balance = BigDecimal.valueOf(10000);
    @Builder.Default
    private String sender = "sender";
    @Builder.Default
    private String receiver = "receiver";
    @Builder.Default
    private LocalDateTime transactionDate = LocalDateTime.now();
    @Builder.Default
    private CurrencyCode currencyCode = CurrencyCode.KRW;
    @Builder.Default
    private Account account = AccountFixture.builder().id(1L).build().account();

    public AccountTransaction accountTransaction() {
        return AccountTransaction.builder()
                .id(id)
                .title(title)
                .type(type)
                .amount(amount)
                .balance(balance)
                .sender(sender)
                .receiver(receiver)
                .transactionDate(transactionDate)
                .currencyCode(currencyCode)
                .account(account)
                .build();
    }
}
