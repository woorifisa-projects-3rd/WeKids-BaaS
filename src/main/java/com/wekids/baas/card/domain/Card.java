package com.wekids.baas.card.domain;

import com.wekids.baas.account.domain.Account;
import com.wekids.baas.card.domain.enums.CardState;
import com.wekids.baas.common.entity.BaseTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
public class Card extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private LocalDate validThru;

    @Column(length = 20, nullable = false)
    private String cvc;

    @Column(nullable = false)
    private String bankMemberName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'ACTIVE'")
    private CardState state;

    private LocalDateTime inactiveDate;

    @Column(nullable = false)
    private LocalDateTime newDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}