package com.wekids.baas.card.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CardCreateRequest {
    @NotBlank
    @Pattern(regexp = "^\\d{4}-\\d{3}-\\d{6}$")
    private String accountNumber;
    @NotNull
    @Positive
    private Long bankMemberId;
    @NotBlank
    @Pattern(regexp = "^\\d{4}")
    private String password;
}
