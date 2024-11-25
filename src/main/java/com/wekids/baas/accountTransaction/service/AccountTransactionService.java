package com.wekids.baas.accountTransaction.service;

import com.wekids.baas.accountTransaction.dto.request.TransactionGetRequest;
import com.wekids.baas.accountTransaction.dto.request.TransferRequest;
import com.wekids.baas.accountTransaction.dto.response.TransactionResponse;
import com.wekids.baas.accountTransaction.dto.response.TransferResponse;

import java.util.List;

public interface AccountTransactionService {
    TransferResponse transfer(TransferRequest transferRequest);

    List<TransactionResponse> getTransactionList(TransactionGetRequest transactionGetRequest);
}
