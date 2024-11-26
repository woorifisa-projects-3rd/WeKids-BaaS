package com.wekids.baas.support.fixture;

import com.wekids.baas.baasMember.domain.BaasMember;
import com.wekids.baas.bankMember.domain.BankMember;
import com.wekids.baas.bankMember.domain.enums.BankMemberState;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public class BankMemberFixture {
    @Builder.Default
    private Long id = 1L;
    @Builder.Default
    private String name = "강현우";
    @Builder.Default
    private LocalDate birthday = LocalDate.of(2012, 5, 15);
    @Builder.Default
    private String residentRegistrationNumber = "120515-3234567";
    @Builder.Default
    private BankMemberState state = BankMemberState.ACTIVE;
    @Builder.Default
    private BaasMember baasMember = BaasMemberFixture.builder().build().baasMember();

    public BankMember bankMember() {
        return BankMember.builder()
                .id(id)
                .name(name)
                .birthday(birthday)
                .residentRegistrationNumber(residentRegistrationNumber)
                .state(state)
                .build();
    }

}
