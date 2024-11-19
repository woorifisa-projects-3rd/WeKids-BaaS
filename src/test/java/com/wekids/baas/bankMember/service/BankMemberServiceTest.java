package com.wekids.baas.bankMember.service;

import com.wekids.baas.baasMember.domain.BaasMember;
import com.wekids.baas.baasMember.repository.BaasMemberRepository;
import com.wekids.baas.bankMember.domain.BankMember;
import com.wekids.baas.bankMember.dto.request.BankMemberCreateRequest;
import com.wekids.baas.bankMember.dto.response.BankMemberCreateResponse;
import com.wekids.baas.bankMember.repository.BankMemberRepository;
import com.wekids.baas.exception.BaasException;
import com.wekids.baas.exception.ErrorCode;
import com.wekids.baas.support.fixture.BaasMemberFixture;
import com.wekids.baas.support.fixture.BankMemberFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BankMemberServiceTest {
    @InjectMocks
    BankMemberServiceImpl bankMemberService;
    @Mock
    BankMemberRepository bankMemberRepository;
    @Mock
    BaasMemberRepository baasMemberRepository;

    @Test
    void 은행_고객_생성_성공() {
        String name = "조다은";
        LocalDate birthday = LocalDate.of(2017, 3, 15);
        String residentRegistrationNumber = "1703154123456";
        Long baasMemberId = 1L;

        BankMemberCreateRequest bankMemberCreateRequest = BankMemberCreateRequest.builder()
                .name(name)
                .birthday(birthday)
                .residentRegistrationNumber(residentRegistrationNumber)
                .baasMemberId(baasMemberId)
                .build();

        BaasMember baasMember = BaasMemberFixture.builder().build().baasMember();
        BankMember bankMember = BankMemberFixture.builder().build().bankMember();

        when(baasMemberRepository.findById(baasMemberId)).thenReturn(Optional.of(baasMember));
        when(bankMemberRepository.save(any(BankMember.class))).thenReturn(bankMember);

        BankMemberCreateResponse bankMemberCreateResponse = bankMemberService.createBankMember(bankMemberCreateRequest);

        assertNotNull(bankMemberCreateResponse);
        assertEquals(bankMember.getId(), bankMemberCreateResponse.getBankMemberId());

        verify(baasMemberRepository, times(1)).findById(baasMemberId);
        verify(bankMemberRepository, times(1)).save(any(BankMember.class));
    }

    @Test
    void baas_고객_없는_경우() {
        String name = "조다은";
        LocalDate birthday = LocalDate.of(2017, 3, 15);
        String residentRegistrationNumber = "1703154123456";
        Long baasMemberId = 1L;

        BankMemberCreateRequest bankMemberCreateRequest = BankMemberCreateRequest.builder()
                .name(name)
                .birthday(birthday)
                .residentRegistrationNumber(residentRegistrationNumber)
                .baasMemberId(baasMemberId)
                .build();

        when(baasMemberRepository.findById(baasMemberId)).thenReturn(Optional.empty());

        BaasException baasException = assertThrows(BaasException.class, () -> bankMemberService.createBankMember(bankMemberCreateRequest));

        assertEquals(ErrorCode.BAAS_MEMBER_NOT_FOUND, baasException.getErrorCode());;
        assertTrue(baasException.getMessage().equals("BaaS 고객 아이디 : " + baasMemberId));

        verify(baasMemberRepository,times(1)).findById(baasMemberId);
    }

}