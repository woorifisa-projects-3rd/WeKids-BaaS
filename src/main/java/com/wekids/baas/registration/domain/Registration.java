package com.wekids.baas.registration.domain;

import com.wekids.baas.baasMember.domain.BaasMember;
import com.wekids.baas.bankMember.domain.BankMember;
import com.wekids.baas.common.entity.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@IdClass(RegistrationId.class)
@Getter
@ToString
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Registration extends BaseTime {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baas_member_id", nullable = false)
    private BaasMember baasMember;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_member_id", nullable = false)
    private BankMember bankMember;

    public static Registration register(BaasMember baasMember, BankMember bankMember) {
        return Registration.builder()
                .baasMember(baasMember)
                .bankMember(bankMember)
                .build();
    }
}
