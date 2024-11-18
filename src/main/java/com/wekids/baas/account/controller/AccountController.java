package com.wekids.baas.account.controller;

import com.wekids.baas.account.dto.request.AccountCreateRequest;
import com.wekids.baas.account.dto.response.AccountCreateResponse;
import com.wekids.baas.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountCreateResponse> createAccount(@RequestBody @Valid AccountCreateRequest accountCreateRequest) {
        AccountCreateResponse response = accountService.createAccount(accountCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
