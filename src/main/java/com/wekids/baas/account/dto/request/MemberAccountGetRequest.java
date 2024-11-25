package com.wekids.baas.account.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberAccountGetRequest {
    @NotBlank
    @Pattern(regexp = "^\\d{4}-\\d{3}-\\d{6}$")
    private String accountNumber;
}
