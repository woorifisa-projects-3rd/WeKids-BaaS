package com.wekids.baas.registration.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class RegisterRequest {
    @NotBlank
    @Pattern(regexp = "^[가-힣A-Za-z]{2,}$")
    private String name;
    @NotNull
    private LocalDate birthday;
    @NotBlank
    @Pattern(regexp = "^\\d{6}-\\d{7}$")
    private String residentRegistrationNumber;
    @NotNull
    @Positive
    private Long baasMemberId;

}
