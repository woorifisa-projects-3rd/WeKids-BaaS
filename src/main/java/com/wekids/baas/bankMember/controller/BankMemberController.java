package com.wekids.baas.bankMember.controller;

import com.wekids.baas.bankMember.dto.request.BankMemberCreateRequest;
import com.wekids.baas.bankMember.dto.response.BankMemberCreateResponse;
import com.wekids.baas.bankMember.service.BankMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bank-members")
@RequiredArgsConstructor
public class BankMemberController {
    private final BankMemberService bankMemberService;

    @PostMapping
    public ResponseEntity<BankMemberCreateResponse> createBankMember(@RequestBody @Valid BankMemberCreateRequest bankMemberCreateRequest) {
        BankMemberCreateResponse response = bankMemberService.createBankMember(bankMemberCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
