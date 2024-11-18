package com.wekids.baas.account.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountCreateRequest {
    @NotNull
    private Long bankMemberId;
    @NotNull
    private Long productId;
    @NotNull
    private String password;
}
