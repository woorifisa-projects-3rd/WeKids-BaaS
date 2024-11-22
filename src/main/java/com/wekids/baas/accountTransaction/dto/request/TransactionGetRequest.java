package com.wekids.baas.accountTransaction.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionGetRequest {
    @NotBlank
    @Size(min = 13, max = 13)
    private String accountNumber;
    @PastOrPresent

    @Builder.Default
    private LocalDate start = LocalDate.now().minusMonths(3);
    @Builder.Default
    private LocalDate end = LocalDate.now();
    @Builder.Default
    private AccountTransactionRequestType type = AccountTransactionRequestType.ALL;
    @PositiveOrZero
    @Builder.Default
    private Integer page = 0;
    @Positive
    @Builder.Default
    private Integer size = 100;
}
