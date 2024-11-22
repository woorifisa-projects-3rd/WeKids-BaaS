package com.wekids.baas.bankMember.service;

import com.wekids.baas.bankMember.dto.request.BankMemberCreateRequest;
import com.wekids.baas.bankMember.dto.response.BankMemberCreateResponse;

public interface BankMemberService {
    BankMemberCreateResponse createBankMember(BankMemberCreateRequest bankMemberCreateRequest);
}
