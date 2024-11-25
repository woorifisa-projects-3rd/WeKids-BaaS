package com.wekids.baas.accountTransaction.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionGetRequest {
    @NotBlank
    @Size(min = 15, max = 15)
    private String accountNumber;
    @PastOrPresent
    @Builder.Default
    private LocalDateTime start = LocalDateTime.now().minusMonths(3);
    @Builder.Default
    private LocalDateTime end = LocalDateTime.now();
    @Builder.Default
    private AccountTransactionRequestType type = AccountTransactionRequestType.ALL;
    @PositiveOrZero
    @Builder.Default
    private Integer page = 0;
    @Positive
    @Builder.Default
    private Integer size = 100;
}
