package com.wekids.baas.accountTransaction.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class TransactionGetRequest {
    @NotBlank
    private String accountNumber;
    @Builder.Default
    private LocalDate start = LocalDate.now().minusMonths(3);
    @Builder.Default
    private LocalDate end = LocalDate.now();
    @Builder.Default
    private String type = "ALL";
    @Builder.Default
    private Integer page = 0;
    @Builder.Default
    private Integer size = 100;
}
