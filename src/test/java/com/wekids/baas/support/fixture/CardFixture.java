package com.wekids.baas.support.fixture;

import com.wekids.baas.account.domain.Account;
import com.wekids.baas.card.domain.Card;
import com.wekids.baas.card.domain.enums.CardState;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public class CardFixture {
    private Long id;
    @Builder.Default
    private String cardNumber = "5159541234567890";
    @Builder.Default
    private LocalDate validThru = LocalDate.now().plusYears(5);
    @Builder.Default
    private String cvc = "123";
    @Builder.Default
    private String bankMemberName = "강현우";
    @Builder.Default
    private String password = "1234";
    @Builder.Default
    private CardState state = CardState.ACTIVE;
    private LocalDateTime inactiveDate;
    @Builder.Default
    private LocalDateTime newDate = LocalDateTime.now();
    @Builder.Default
    private Account account = AccountFixture.builder().id(1L).build().account();

    public Card card() {
        return Card.builder()
                .id(id)
                .cardNumber(cardNumber)
                .validThru(validThru)
                .cvc(cvc)
                .bankMemberName(bankMemberName)
                .password(password)
                .state(state)
                .inactiveDate(inactiveDate)
                .newDate(newDate)
                .account(account)
                .build();
    }
}
