package com.wekids.baas.bankMember.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    @Size(min = 14, max = 14)
    private String residentRegistrationNumber;
    @NotNull
    @Positive
    private Long baasMemberId;

}
