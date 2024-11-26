package com.wekids.baas.accountTransaction.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class TransferRequest {
    @NotBlank
    @Pattern(regexp = "^\\d{4}-\\d{3}-\\d{6}$")
    private String senderAccountNumber;
    @NotBlank
    @Pattern(regexp = "^\\d{4}-\\d{3}-\\d{6}$")
    private String receiverAccountNumber;

    @NotNull
    @Positive
    private BigDecimal amount;
}
