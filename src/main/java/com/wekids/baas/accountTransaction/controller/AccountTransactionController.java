package com.wekids.baas.accountTransaction.controller;

import com.wekids.baas.accountTransaction.dto.request.TransactionGetRequest;
import com.wekids.baas.accountTransaction.dto.request.TransferRequest;
import com.wekids.baas.accountTransaction.dto.response.TransactionResponse;
import com.wekids.baas.accountTransaction.dto.response.TransferResponse;
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
    public ResponseEntity<TransferResponse> transfer(@RequestBody @Valid TransferRequest transferRequest) {
        TransferResponse response = accountTransactionService.transfer(transferRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/getTransactions")
    public ResponseEntity<List<TransactionResponse>> getTransactionList(@RequestBody @Valid TransactionGetRequest transactionGetRequest) {
        List<TransactionResponse> response = accountTransactionService.getTransactionList(transactionGetRequest);
        return ResponseEntity.ok(response);
    }

}
