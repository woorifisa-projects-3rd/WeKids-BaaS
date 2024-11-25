package com.wekids.baas.registration.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterResponse {
    private Long bankMemberId;

    public static RegisterResponse of(Long bankMemberId){
        return new RegisterResponse(bankMemberId);
    }
}
