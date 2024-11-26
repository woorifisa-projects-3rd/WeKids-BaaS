package com.wekids.baas.bankMember.service;

import com.wekids.baas.bankMember.dto.request.BankMemberCreateRequest;
import com.wekids.baas.bankMember.dto.request.BankMemberIdGetRequest;
import com.wekids.baas.bankMember.dto.response.BankMemberIdResponse;

public interface BankMemberService {
    BankMemberIdResponse createBankMember(BankMemberCreateRequest bankMemberCreateRequest);
    BankMemberIdResponse getBankMemberId(BankMemberIdGetRequest bankMemberIdGetRequest);
}
