package com.wekids.baas.account.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountCreateRequest {
    @NotNull
    @Positive
    private Long bankMemberId;
    @NotNull
    @Positive
    private Long productId;
    @NotBlank
    private String password;
}
