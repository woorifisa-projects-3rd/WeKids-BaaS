package com.wekids.baas.card.service;

import com.wekids.baas.account.domain.Account;
import com.wekids.baas.account.repository.AccountRepository;
import com.wekids.baas.bankMember.domain.BankMember;
import com.wekids.baas.bankMember.repository.BankMemberRepository;
import com.wekids.baas.card.domain.Card;
import com.wekids.baas.card.dto.request.CardCreateRequest;
import com.wekids.baas.card.dto.response.CardCreateResponse;
import com.wekids.baas.card.repository.CardRepository;
import com.wekids.baas.exception.BaasException;
import com.wekids.baas.exception.ErrorCode;
import com.wekids.baas.support.fixture.AccountFixture;
import com.wekids.baas.support.fixture.BankMemberFixture;
import com.wekids.baas.support.fixture.CardFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {
    @InjectMocks
    CardServiceImpl cardService;
    @Mock
    CardRepository cardRepository;
    @Mock
    AccountRepository accountRepository;
    @Mock
    BankMemberRepository bankMemberRepository;


    @Test
    void 카드_생성_성공() {
        String accountNumber = "1002123456789";
        Long bankMemberId = 1L;
        String password = "1234";

        CardCreateRequest cardCreateRequest = CardCreateRequest.builder()
                .accountNumber(accountNumber)
                .bankMemberId(bankMemberId)
                .password(password)
                .build();

        Account account = AccountFixture.builder().id(1L).build().account();
        BankMember bankMember = BankMemberFixture.builder().build().bankMember();
        Card card = CardFixture.builder().id(1L).build().card();

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
        when(bankMemberRepository.findById(bankMemberId)).thenReturn(Optional.of(bankMember));
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        CardCreateResponse cardCreateResponse = cardService.createCard(cardCreateRequest);

        assertNotNull(cardCreateResponse);
        assertEquals(card.getCardNumber(), cardCreateResponse.getCardNumber());
        assertEquals(card.getValidThru(), cardCreateResponse.getValidThru());
        assertEquals(card.getCvc(), cardCreateResponse.getCvc());
        assertEquals(card.getBankMemberName(), cardCreateResponse.getBankMemberName());
        assertEquals(card.getNewDate(), cardCreateResponse.getNewDate());

        verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
        verify(bankMemberRepository, times(1)).findById(bankMemberId);
        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    void 계좌번호에_해당하는_계좌가_없을_경우() {
        String accountNumber = "1002123456789";
        Long bankMemberId = 1L;
        String password = "1234";

        CardCreateRequest cardCreateRequest = CardCreateRequest.builder()
                .accountNumber(accountNumber)
                .bankMemberId(bankMemberId)
                .password(password)
                .build();

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

        BaasException baasException = assertThrows(BaasException.class, () -> cardService.createCard(cardCreateRequest));

        assertEquals(ErrorCode.ACCOUNT_NOT_FOUND, baasException.getErrorCode());
        assertTrue(baasException.getMessage().equals("계좌 번호: " + accountNumber));

        verify(accountRepository, times(1)).findByAccountNumber(accountNumber);

    }

    @Test
    void 은행_고객_아이디에_해당하는_고객이_없을_경우() {
        String accountNumber = "1002123456789";
        Long bankMemberId = 1L;
        String password = "1234";

        CardCreateRequest cardCreateRequest = CardCreateRequest.builder()
                .accountNumber(accountNumber)
                .bankMemberId(bankMemberId)
                .password(password)
                .build();

        Account account = AccountFixture.builder().id(1L).build().account();

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
        when(bankMemberRepository.findById(bankMemberId)).thenReturn(Optional.empty());

        BaasException baasException = assertThrows(BaasException.class, () -> cardService.createCard(cardCreateRequest));

        assertEquals(ErrorCode.BANK_MEMBER_NOT_FOUND, baasException.getErrorCode());
        assertTrue(baasException.getMessage().equals("은행 고객 아이디: " + bankMemberId));

        verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
        verify(bankMemberRepository, times(1)).findById(bankMemberId);
    }

}