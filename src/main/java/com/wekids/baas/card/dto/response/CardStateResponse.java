package com.wekids.baas.card.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class CardStateResponse {
    private LocalDateTime inactiveDate;

    public static CardStateResponse from(LocalDateTime inactiveDate){
        return new CardStateResponse(inactiveDate);
    }
}
