package com.wekids.baas.card.service;

import com.wekids.baas.account.domain.Account;
import com.wekids.baas.account.repository.AccountRepository;
import com.wekids.baas.card.domain.Card;
import com.wekids.baas.card.dto.request.CardCreateRequest;
import com.wekids.baas.card.dto.response.CardCreateResponse;
import com.wekids.baas.card.repository.CardRepository;
import com.wekids.baas.exception.BaasException;
import com.wekids.baas.exception.ErrorCode;
import com.wekids.baas.support.fixture.AccountFixture;
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


    @Test
    void 카드_생성_성공() {
        String accountNumber = "1002123456789";
        String bankMemberName = "강현우";
        String password = "1234";

        CardCreateRequest cardCreateRequest = CardCreateRequest.builder()
                .accountNumber(accountNumber)
                .bankMemberName(bankMemberName)
                .password(password)
                .build();

        Account account = AccountFixture.builder().id(1L).build().account();
        Card card = CardFixture.builder().id(1L).build().card();

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        CardCreateResponse cardCreateResponse = cardService.createCard(cardCreateRequest);

        assertNotNull(cardCreateResponse);
        assertEquals(card.getCardNumber(), cardCreateResponse.getCardNumber());
        assertEquals(card.getValidThru(), cardCreateResponse.getValidThru());
        assertEquals(card.getCvc(), cardCreateResponse.getCvc());
        assertEquals(card.getBankMemberName(), cardCreateResponse.getBankMemberName());
        assertEquals(card.getNewDate(), cardCreateResponse.getNewDate());

        verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    void 계좌번호에_해당하는_계좌가_없을_경우() {
        String accountNumber = "1002123456789";
        String bankMemberName = "강현우";
        String password = "1234";

        CardCreateRequest cardCreateRequest = CardCreateRequest.builder()
                .accountNumber(accountNumber)
                .bankMemberName(bankMemberName)
                .password(password)
                .build();

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

        BaasException baasException = assertThrows(BaasException.class, () -> cardService.createCard(cardCreateRequest));

        assertEquals(ErrorCode.ACCOUNT_NOT_FOUND, baasException.getErrorCode());
        assertTrue(baasException.getMessage().equals("계좌 번호: " + accountNumber));

        verify(accountRepository, times(1)).findByAccountNumber(accountNumber);

    }

}