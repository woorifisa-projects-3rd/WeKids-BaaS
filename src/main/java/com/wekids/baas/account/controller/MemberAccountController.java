package com.wekids.baas.account.controller;

import com.wekids.baas.account.dto.response.MemberAccountGetResponse;
import com.wekids.baas.account.service.AccountService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/baas-members/{baasMemberId}/bank-members/{bankMemberId}/accounts")
@RequiredArgsConstructor
public class MemberAccountController {
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<MemberAccountGetResponse>> getMemberAccountList(@PathVariable("baasMemberId") Long baasMemberId, @PathVariable("bankMemberId") Long bankMemberId) {
        List<MemberAccountGetResponse> result = accountService.getMemberAccountList(baasMemberId, bankMemberId);
        return ResponseEntity.ok(result);
    }
}
