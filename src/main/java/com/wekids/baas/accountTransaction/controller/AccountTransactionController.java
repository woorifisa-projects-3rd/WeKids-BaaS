package com.wekids.baas.accountTransaction.controller;

import com.wekids.baas.accountTransaction.domain.enums.AccountTransactionType;
import com.wekids.baas.accountTransaction.domain.enums.CurrencyCode;
import com.wekids.baas.accountTransaction.dto.request.TransactionGetRequest;
import com.wekids.baas.accountTransaction.dto.request.TransferRequest;
import com.wekids.baas.accountTransaction.dto.response.TransactionGetResponse;
import com.wekids.baas.accountTransaction.service.AccountTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AccountTransactionController {
    private final AccountTransactionService accountTransactionService;

    @PostMapping("/transactions")
    public ResponseEntity<Void> transfer(@RequestBody TransferRequest transferRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/getTransactions")
    public ResponseEntity<List<TransactionGetResponse>> getTransactionList(@RequestBody TransactionGetRequest transactionGetRequest) {
        TransactionGetResponse transactionGetResponse = TransactionGetResponse.builder()
                .title("카카오페이")
                .type(AccountTransactionType.DEPOSIT.name())
                .amount(1000L)
                .balance(19862L)
                .sender("조예은")
                .receiver("강현우")
                .currencyCode(CurrencyCode.KRW.name())
                .transactionDate(LocalDateTime.now())
                .build();
        List<TransactionGetResponse> result = List.of(transactionGetResponse);
        return ResponseEntity.ok(result);
    }

}
