package com.wekids.baas.card.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CardCreateRequest {
    @NotBlank
    @Size(min = 15, max = 15)
    private String accountNumber;
    @NotNull
    @Positive
    private Long bankMemberId;
    @NotBlank
    @Size(min = 4, max = 4)
    private String password;

}
