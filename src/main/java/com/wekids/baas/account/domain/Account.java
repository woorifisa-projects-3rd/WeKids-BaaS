package com.wekids.baas.account.domain;

import com.wekids.baas.account.domain.enums.AccountState;
import com.wekids.baas.account.domain.enums.BankCode;
import com.wekids.baas.bankMember.domain.BankMember;
import com.wekids.baas.common.entity.BaseTime;
import com.wekids.baas.product.domain.Product;
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
public class Account extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String accountNumber;

    @Column(precision = 20, scale = 2)
    private BigDecimal balance;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime expireDate;

    private LocalDateTime inactiveDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @ToString.Exclude
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_member_id", nullable = false)
    @ToString.Exclude
    private BankMember bankMember;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BankCode bankCode;

    public static Account createNewAccount(String accountNumber, String password, LocalDateTime expireDate, Product product, BankMember bankMember) {
        return Account.builder()
                .accountNumber(accountNumber)
                .balance(BigDecimal.ZERO)
                .password(password)
                .expireDate(expireDate)
                .state(AccountState.ACTIVE)
                .product(product)
                .bankMember(bankMember)
                .bankCode(BankCode.WOORI_BANK)
                .build();
    }

    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }
}
