package com.wekids.baas.account.controller;

import com.wekids.baas.account.domain.enums.AccountState;
import com.wekids.baas.account.domain.enums.BankCode;
import com.wekids.baas.account.dto.response.MemberAccountGetResponse;
import com.wekids.baas.account.service.AccountService;
import com.wekids.baas.product.domain.enums.ProductType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/baas-members/{baasMemberId}/bank-members/{bankMemberId}/accounts")
@RequiredArgsConstructor
public class MemberAccountController {
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<MemberAccountGetResponse>> getMemberAccountList(@PathVariable Long baasMemberId, @PathVariable Long bankMemberId) {
        MemberAccountGetResponse memberAccountGetResponse = MemberAccountGetResponse.builder()
                .accountNumber("1111111111111")
                .bankName(BankCode.WOORI_BANK.getName())
                .balance(BigDecimal.valueOf(999999))
                .state(AccountState.ACTIVE.name())
                .bankMemberName("강현우")
                .productName("우리 아이 행복 통장")
                .productType(ProductType.CHECKING.name())
                .build();

        List<MemberAccountGetResponse> result = List.of(memberAccountGetResponse);
        return ResponseEntity.ok(result);
    }
}
