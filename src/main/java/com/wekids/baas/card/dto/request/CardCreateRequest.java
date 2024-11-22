package com.wekids.baas.card.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CardCreateRequest {
    @NotBlank
    @Size(min = 13, max = 13)
    private String accountNumber;
    @NotBlank
    @Size(min = 2)
    private String bankMemberName;
    @NotBlank
    @Size(min = 4, max = 4)
    private String password;

}
