package com.wekids.baas.registration.service;

import com.wekids.baas.registration.dto.request.RegisterRequest;
import com.wekids.baas.registration.dto.response.RegisterResponse;

public interface RegistrationService {
    RegisterResponse register(RegisterRequest registerRequest);
}
