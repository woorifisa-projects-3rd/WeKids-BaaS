package com.wekids.baas.accountTransaction.controller;

import com.wekids.baas.accountTransaction.dto.request.TransactionGetRequest;
import com.wekids.baas.accountTransaction.dto.request.TransferRequest;
import com.wekids.baas.accountTransaction.dto.response.TransactionGetResponse;
import com.wekids.baas.accountTransaction.service.AccountTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AccountTransactionController {
    private final AccountTransactionService accountTransactionService;

    @PostMapping("/transactions")
    public ResponseEntity<Void> transfer(@RequestBody @Valid TransferRequest transferRequest) {
        accountTransactionService.transfer(transferRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/getTransactions")
    public ResponseEntity<List<TransactionGetResponse>> getTransactionList(@RequestBody @Valid TransactionGetRequest transactionGetRequest) {
        List<TransactionGetResponse> result = accountTransactionService.getTransactionList(transactionGetRequest);
        return ResponseEntity.ok(result);
    }

}
