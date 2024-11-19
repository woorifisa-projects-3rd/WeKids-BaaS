package com.wekids.baas.bankMember.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankMemberCreateResponse {
    private Long bankMemberId;

    public static BankMemberCreateResponse of(Long bankMemberId){
        return new BankMemberCreateResponse(bankMemberId);
    }
}
