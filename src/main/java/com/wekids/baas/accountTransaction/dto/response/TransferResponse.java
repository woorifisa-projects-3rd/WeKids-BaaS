package com.wekids.baas.accountTransaction.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransferResponse {
    private TransactionResponse sender;
    private TransactionResponse receiver;

    public static TransferResponse of(TransactionResponse sender, TransactionResponse receiver) {
        return TransferResponse.builder()
                .sender(sender)
                .receiver(receiver)
                .build();
    }
}
