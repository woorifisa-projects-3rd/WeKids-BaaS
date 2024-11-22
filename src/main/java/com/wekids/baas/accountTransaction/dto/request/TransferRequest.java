package com.wekids.baas.accountTransaction.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class TransferRequest {
    @NotBlank
    private String senderAccountNumber;
    @NotBlank
    private String receiverAccountNumber;
    @Min(1)
    private BigDecimal amount;
}
