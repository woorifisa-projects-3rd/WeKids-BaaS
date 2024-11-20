package com.wekids.baas.accountTransaction.service;

import com.wekids.baas.account.domain.Account;
import com.wekids.baas.account.domain.enums.AccountState;
import com.wekids.baas.account.repository.AccountRepository;
import com.wekids.baas.accountTransaction.domain.AccountTransaction;
import com.wekids.baas.accountTransaction.dto.request.TransferRequest;
import com.wekids.baas.accountTransaction.repository.AccountTransactionRepository;
import com.wekids.baas.exception.BaasException;
import com.wekids.baas.exception.ErrorCode;
import com.wekids.baas.support.fixture.AccountFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountTransactionServiceTest {
    @InjectMocks
    AccountTransactionServiceImpl accountTransactionService;
    @Mock
    AccountTransactionRepository accountTransactionRepository;
    @Mock
    AccountRepository accountRepository;

    @Nested
    class TransferTest {
        String senderAccountNumber;
        String receiverAccountNumber;
        BigDecimal amount;
        TransferRequest transferRequest;

        @BeforeEach
        void setUp() {
            senderAccountNumber = "1234567891234";
            receiverAccountNumber = "2345678912345";
            amount = BigDecimal.TEN;

            transferRequest = TransferRequest.builder()
                    .senderAccountNumber(senderAccountNumber)
                    .receiverAccountNumber(receiverAccountNumber)
                    .amount(amount)
                    .build();
        }

        @Test
        void 이체_성공() {
            Account senderAccount = AccountFixture.builder().accountNumber(senderAccountNumber).balance(BigDecimal.TEN).build().account();
            Account receiverAccount = AccountFixture.builder().accountNumber(receiverAccountNumber).balance(BigDecimal.ZERO).build().account();

            when(accountRepository.findByAccountNumber(senderAccountNumber)).thenReturn(Optional.of(senderAccount));
            when(accountRepository.findByAccountNumber(receiverAccountNumber)).thenReturn(Optional.of(receiverAccount));

            accountTransactionService.transfer(transferRequest);

            assertEquals(BigDecimal.ZERO, senderAccount.getBalance()); // 송신자의 잔액이 감소
            assertEquals(BigDecimal.TEN, receiverAccount.getBalance()); // 수신자의 잔액이 증가

            verify(accountRepository, times(1)).findByAccountNumber(senderAccountNumber);
            verify(accountRepository, times(1)).findByAccountNumber(receiverAccountNumber);
            verify(accountTransactionRepository, times(2)).save(any(AccountTransaction.class));
        }

        @Test
        void 잔액부족_에러() {
            Account senderAccount = AccountFixture.builder().accountNumber(senderAccountNumber).balance(BigDecimal.ONE).build().account();
            Account receiverAccount = AccountFixture.builder().accountNumber(receiverAccountNumber).balance(BigDecimal.ZERO).build().account();

            when(accountRepository.findByAccountNumber(senderAccountNumber)).thenReturn(Optional.of(senderAccount));
            when(accountRepository.findByAccountNumber(receiverAccountNumber)).thenReturn(Optional.of(receiverAccount));

            BaasException exception = assertThrows(BaasException.class, () -> accountTransactionService.transfer(transferRequest));
            assertEquals(ErrorCode.INSUFFICIENT_BALANCE, exception.getErrorCode());
            verify(accountTransactionRepository, never()).save(any(AccountTransaction.class));
        }

        @Test
        void 송신자와_수신자가_동일한_계좌번호_에러() {
            transferRequest = TransferRequest.builder()
                    .senderAccountNumber(senderAccountNumber)
                    .receiverAccountNumber(senderAccountNumber) // 동일한 계좌 번호
                    .amount(amount)
                    .build();

            Account senderAccount = AccountFixture.builder().accountNumber(senderAccountNumber).balance(BigDecimal.TEN).build().account();

            when(accountRepository.findByAccountNumber(senderAccountNumber)).thenReturn(Optional.of(senderAccount));

            BaasException exception = assertThrows(BaasException.class, () -> accountTransactionService.transfer(transferRequest));
            assertEquals(ErrorCode.SENDER_AND_RECEIVER_SAME, exception.getErrorCode());
            verify(accountTransactionRepository, never()).save(any(AccountTransaction.class));
        }

        @Test
        void 송신자_계좌_찾을_수_없음_에러() {
            when(accountRepository.findByAccountNumber(senderAccountNumber)).thenReturn(Optional.empty());

            BaasException exception = assertThrows(BaasException.class, () -> accountTransactionService.transfer(transferRequest));
            assertEquals(ErrorCode.ACCOUNT_NOT_FOUND, exception.getErrorCode());
            verify(accountTransactionRepository, never()).save(any(AccountTransaction.class));
        }

        @Test
        void 수신자_계좌_찾을_수_없음_에러() {
            Account senderAccount = AccountFixture.builder().accountNumber(senderAccountNumber).balance(BigDecimal.TEN).build().account();

            when(accountRepository.findByAccountNumber(senderAccountNumber)).thenReturn(Optional.of(senderAccount));
            when(accountRepository.findByAccountNumber(receiverAccountNumber)).thenReturn(Optional.empty());

            BaasException exception = assertThrows(BaasException.class, () -> accountTransactionService.transfer(transferRequest));
            assertEquals(ErrorCode.ACCOUNT_NOT_FOUND, exception.getErrorCode());
            verify(accountTransactionRepository, never()).save(any(AccountTransaction.class));
        }

        @Test
        void 송신_계좌_비활성화_에러() {
            Account senderAccount = AccountFixture.builder().accountNumber(senderAccountNumber).balance(BigDecimal.TEN).build().account();
            Account receiverAccount = AccountFixture.builder().accountNumber(receiverAccountNumber).balance(BigDecimal.ZERO).state(AccountState.INACTIVE).build().account();

            when(accountRepository.findByAccountNumber(senderAccountNumber)).thenReturn(Optional.of(senderAccount));
            when(accountRepository.findByAccountNumber(receiverAccountNumber)).thenReturn(Optional.of(receiverAccount));

            BaasException exception = assertThrows(BaasException.class, () -> accountTransactionService.transfer(transferRequest));
            assertEquals(ErrorCode.ACCOUNT_INACTIVE, exception.getErrorCode());
            verify(accountTransactionRepository, never()).save(any(AccountTransaction.class));
        }
    }
}