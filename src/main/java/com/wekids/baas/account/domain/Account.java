package com.wekids.baas.account.domain;

import com.wekids.baas.account.domain.enums.AccountState;
import com.wekids.baas.account.domain.enums.AccountType;
import com.wekids.baas.bank.domain.Bank;
import com.wekids.baas.bankMember.domain.BankMember;
import com.wekids.baas.common.entity.BaseTime;
import com.wekids.baas.product.domain.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType type;

    @Column(nullable = false)
    private LocalDateTime expireDate;

    private LocalDateTime inactiveDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_member_id", nullable = false)
    private BankMember bankMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_code", nullable = false)
    private Bank bank;
}
