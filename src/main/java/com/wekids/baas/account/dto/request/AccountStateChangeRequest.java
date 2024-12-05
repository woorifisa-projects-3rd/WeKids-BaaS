package com.wekids.baas.account.dto.request;

import com.wekids.baas.account.domain.enums.AccountState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class AccountStateChangeRequest {
    @NotNull
    @Positive
    private Long bankMemberId;

    @NotNull
    @Positive
    private Long baasMemberId;

    @NotBlank
    @Pattern(regexp = "^\\d{4}-\\d{3}-\\d{6}$")
    private String accountNumber;

    @NotBlank
    private String password;

    @NotNull
    private AccountState state;
}
