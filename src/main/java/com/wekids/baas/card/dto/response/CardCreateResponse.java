package com.wekids.baas.card.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class CardCreateResponse {
    private String cardNumber;
    private LocalDate validThru;
    private String cvc;
    private String bankMemberName;
    private LocalDateTime newDate;

    public static CardCreateResponse of(String cardNumber, LocalDate validThru, String cvc, String bankMemberName, LocalDateTime newDate) {
        return CardCreateResponse.builder()
                .cardNumber(cardNumber)
                .validThru(validThru)
                .cvc(cvc)
                .bankMemberName(bankMemberName)
                .newDate(newDate)
                .build();
    }
}
