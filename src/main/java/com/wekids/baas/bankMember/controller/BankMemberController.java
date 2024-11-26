package com.wekids.baas.bankMember.controller;

import com.wekids.baas.bankMember.dto.request.BankMemberCreateRequest;
import com.wekids.baas.bankMember.dto.request.BankMemberIdGetRequest;
import com.wekids.baas.bankMember.dto.response.BankMemberIdResponse;
import com.wekids.baas.bankMember.service.BankMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bank-members")
@RequiredArgsConstructor
public class BankMemberController {
    private final BankMemberService bankMemberService;

    @PostMapping
    public ResponseEntity<BankMemberIdResponse> createBankMember(@RequestBody @Valid BankMemberCreateRequest bankMemberCreateRequest) {
        BankMemberIdResponse response = bankMemberService.createBankMember(bankMemberCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/getId")
    public ResponseEntity<BankMemberIdResponse> getBankMemberId(@RequestBody @Valid BankMemberIdGetRequest bankMemberIdGetRequest) {
        BankMemberIdResponse bankMemberId = bankMemberService.getBankMemberId(bankMemberIdGetRequest);
        return ResponseEntity.ok(bankMemberId);
    }
}
