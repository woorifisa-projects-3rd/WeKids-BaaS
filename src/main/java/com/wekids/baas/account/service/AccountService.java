package com.wekids.baas.account.service;

import com.wekids.baas.account.dto.request.AccountCreateRequest;
import com.wekids.baas.account.dto.request.MemberAccountGetRequest;
import com.wekids.baas.account.dto.response.AccountCreateResponse;
import com.wekids.baas.account.dto.response.MemberAccountGetResponse;

import java.util.List;

public interface AccountService {
    AccountCreateResponse createAccount(AccountCreateRequest accountCreateRequest);
    List<MemberAccountGetResponse> getMemberAccountList(Long baasMemberId, Long bankMemberId);
    MemberAccountGetResponse getMemberAccount(MemberAccountGetRequest memberAccountGetRequest);
}
