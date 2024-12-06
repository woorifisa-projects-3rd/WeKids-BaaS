package com.wekids.baas.card.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class CardStateChangeResponse {
    private LocalDateTime inactiveDate;

    public static CardStateChangeResponse from(LocalDateTime inactiveDate){
        return new CardStateChangeResponse(inactiveDate);
    }
}
