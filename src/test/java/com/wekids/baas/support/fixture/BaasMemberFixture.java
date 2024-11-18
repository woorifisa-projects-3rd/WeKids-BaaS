package com.wekids.baas.support.fixture;

import com.wekids.baas.baasMember.domain.BaasMember;
import com.wekids.baas.baasMember.domain.enums.BaasMemberState;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class BaasMemberFixture {
    @Builder.Default
    private Long id = 1L;
    @Builder.Default
    private String businessRegistrationNumber = "123-45-67890";
    @Builder.Default
    private String companyName = "WeKids";
    @Builder.Default
    private String email = "wekids@wekids.co.kr";
    @Builder.Default
    private String password = "password";
    @Builder.Default
    private String address = "서울 마포구 월드컵북로 434 상암 IT 타워";
    @Builder.Default
    private String phone = "02-1234-5678";
    @Builder.Default
    private BaasMemberState state = BaasMemberState.ACTIVE;
    private LocalDateTime inactiveDate;

    public BaasMember baasMember() {
        return BaasMember.builder()
                .id(id)
                .businessRegistrationNumber(businessRegistrationNumber)
                .companyName(companyName)
                .email(email)
                .password(password)
                .address(address)
                .phone(phone)
                .state(state)
                .inactiveDate(inactiveDate)
                .build();
    }
}
