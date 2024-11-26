package com.wekids.baas.bankMember.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankMemberIdResponse {
    private Long bankMemberId;

    public static BankMemberIdResponse of(Long bankMemberId){
        return new BankMemberIdResponse(bankMemberId);
    }
}
