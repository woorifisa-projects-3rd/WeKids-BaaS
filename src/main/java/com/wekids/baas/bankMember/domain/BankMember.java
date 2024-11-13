package com.wekids.baas.bankMember.domain;

import com.wekids.baas.baasMember.domain.BaasMember;
import com.wekids.baas.bankMember.domain.enums.BankMemberState;
import com.wekids.baas.common.entity.BaseTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@ToString
public class BankMember extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    private String residentRegistrationNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'ACTIVE'")
    private BankMemberState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baas_member_id", nullable = false)
    private BaasMember baasMember;
}