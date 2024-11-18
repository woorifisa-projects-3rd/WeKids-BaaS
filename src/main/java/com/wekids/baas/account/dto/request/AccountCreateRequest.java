package com.wekids.baas.account.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountCreateRequest {
    Long bankMemberId;
    Long productId;
    String password;
}
