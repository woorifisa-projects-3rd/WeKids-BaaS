package com.wekids.baas.accountTransaction.service;

import com.wekids.baas.accountTransaction.dto.request.TransactionGetRequest;
import com.wekids.baas.accountTransaction.dto.request.TransferRequest;
import com.wekids.baas.accountTransaction.dto.response.TransactionGetResponse;

import java.util.List;

public interface AccountTransactionService {
    void transfer(TransferRequest transferRequest);
}
