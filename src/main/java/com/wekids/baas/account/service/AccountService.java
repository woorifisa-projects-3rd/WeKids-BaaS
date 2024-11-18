package com.wekids.baas.account.service;

import com.wekids.baas.account.dto.request.AccountCreateRequest;
import com.wekids.baas.account.dto.response.AccountCreateResponse;

public interface AccountService {
    AccountCreateResponse createAccount(AccountCreateRequest accountCreateRequest);
}
