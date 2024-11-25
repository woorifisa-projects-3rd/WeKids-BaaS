package com.wekids.baas.registration.domain;

import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class RegistrationId implements Serializable {
    private Long bankMember;
    private Long baasMember;

    public static RegistrationId of(Long baasMemberId, Long bankMemberId) {
        return RegistrationId.builder()
                .baasMember(baasMemberId)
                .bankMember(bankMemberId)
                .build();
    }
}
