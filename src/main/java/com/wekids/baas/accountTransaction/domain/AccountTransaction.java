package com.wekids.baas.accountTransaction.domain;

import com.wekids.baas.account.domain.Account;
import com.wekids.baas.accountTransaction.domain.enums.AccountTransactionType;
import com.wekids.baas.accountTransaction.domain.enums.CurrencyCode;
import com.wekids.baas.common.entity.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountTransaction extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountTransactionType type;

    @Column(precision = 20, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(precision = 20, scale = 2, nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private String receiver;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CurrencyCode currencyCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public static AccountTransaction createNewAccountTransaction(String title, AccountTransactionType type, BigDecimal amount, BigDecimal balance, String sender, String receiver, LocalDateTime transactionDate, CurrencyCode currencyCode, Account account) {
        return AccountTransaction.builder()
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