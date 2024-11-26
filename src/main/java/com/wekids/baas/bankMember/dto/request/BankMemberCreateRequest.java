package com.wekids.baas.bankMember.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class BankMemberCreateRequest {
    @NotBlank
    @Size(min = 2)
    private String name;
    @NotNull
    private LocalDate birthday;
    @NotBlank
    @Pattern(regexp = "^\\d{6}-\\d{7}$")
    private String residentRegistrationNumber;
}
