package com.wekids.baas.card.dto.request;

import com.wekids.baas.card.domain.enums.CardState;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class CardStateChangeRequest {
    @NotNull
    private Long bankMemberId;

    @NotNull
    private Long baasMemberId;

    @NotBlank
    private String cardNumber;

    @NotBlank
    @Size(min = 3, max = 3)
    private String cvc;

    @NotBlank
    @Size(min = 4, max = 4)
    private String password;

    @NotNull
    private CardState state;
}