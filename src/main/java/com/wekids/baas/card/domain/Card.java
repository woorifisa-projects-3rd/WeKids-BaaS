package com.wekids.baas.card.domain;

import com.wekids.baas.account.domain.Account;
import com.wekids.baas.card.domain.enums.CardState;
import com.wekids.baas.common.entity.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public static Card createNewCard(String cardNumber, LocalDate validThru, String cvc, String bankMemberName, String password, Account account) {
        return Card.builder()
                .cardNumber(cardNumber)
                .validThru(validThru)
                .cvc(cvc)
                .bankMemberName(bankMemberName)
                .state(CardState.ACTIVE)
                .password(password)
                .newDate(LocalDateTime.now())
                .account(account)
                .build();
    }
}