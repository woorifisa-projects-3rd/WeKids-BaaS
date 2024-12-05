package com.wekids.baas.account.controller;

import com.wekids.baas.account.dto.request.AccountCreateRequest;
import com.wekids.baas.account.dto.request.AccountStateChangeRequest;
import com.wekids.baas.account.dto.request.MemberAccountGetRequest;
import com.wekids.baas.account.dto.response.AccountCreateResponse;
import com.wekids.baas.account.dto.response.AccountStateChangeResponse;
import com.wekids.baas.account.dto.response.MemberAccountGetResponse;
import com.wekids.baas.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/accounts")
    public ResponseEntity<AccountCreateResponse> createAccount(@RequestBody @Valid AccountCreateRequest accountCreateRequest) {
        AccountCreateResponse response = accountService.createAccount(accountCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/baas-members/{baasMemberId}/bank-members/{bankMemberId}/accounts")
    public ResponseEntity<List<MemberAccountGetResponse>> getMemberAccountList(@PathVariable("baasMemberId") Long baasMemberId, @PathVariable("bankMemberId") Long bankMemberId) {
        List<MemberAccountGetResponse> response = accountService.getMemberAccountList(baasMemberId, bankMemberId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/getAccounts")
    public ResponseEntity<MemberAccountGetResponse> getMemberAccount(@RequestBody @Valid MemberAccountGetRequest memberAccountGetRequest) {
        MemberAccountGetResponse response = accountService.getMemberAccount(memberAccountGetRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/accounts/state")
    public ResponseEntity<AccountStateChangeResponse> changeAccountState(@RequestBody @Valid AccountStateChangeRequest request){
        AccountStateChangeResponse response = accountService.changeAccountState(request);
        return ResponseEntity.ok(response);
    }
}
