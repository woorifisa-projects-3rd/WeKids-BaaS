package com.wekids.baas.registration.controller;

import com.wekids.baas.registration.dto.request.RegisterRequest;
import com.wekids.baas.registration.dto.response.RegisterResponse;
import com.wekids.baas.registration.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<RegisterResponse> registerService(@RequestBody @Valid RegisterRequest registerRequest) {
        RegisterResponse response = registrationService.register(registerRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
