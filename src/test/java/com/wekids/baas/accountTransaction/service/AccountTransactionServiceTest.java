package com.wekids.baas.accountTransaction.service;

import com.wekids.baas.account.domain.Account;
import com.wekids.baas.account.domain.enums.AccountState;
import com.wekids.baas.account.repository.AccountRepository;
import com.wekids.baas.accountTransaction.domain.AccountTransaction;
import com.wekids.baas.accountTransaction.domain.enums.AccountTransactionType;
import com.wekids.baas.accountTransaction.dto.request.AccountTransactionRequestType;
import com.wekids.baas.accountTransaction.dto.request.TransactionGetRequest;
import com.wekids.baas.accountTransaction.dto.request.TransferRequest;
import com.wekids.baas.accountTransaction.dto.response.TransactionGetResponse;
import com.wekids.baas.accountTransaction.repository.AccountTransactionRepository;
import com.wekids.baas.exception.BaasException;
import com.wekids.baas.exception.ErrorCode;
import com.wekids.baas.support.fixture.AccountFixture;
import com.wekids.baas.support.fixture.AccountTransactionFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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
            assertEquals(ErrorCode.INACTIVE_ACCOUNT, exception.getErrorCode());
            verify(accountTransactionRepository, never()).save(any(AccountTransaction.class));
        }
    }

    @Nested
    class getTransactionListTest {
        String accountNumber;
        LocalDate start;
        LocalDate end;
        AccountTransactionRequestType type;
        Integer page;
        Integer size;
        TransactionGetRequest transactionGetRequest;
        LocalDateTime startTime;
        LocalDateTime endTime;
        AccountTransactionType accountTransactionType;
        PageRequest pageRequest;

        @BeforeEach
        void setUp() {
            accountNumber = "1002123456789";
            start = LocalDate.of(2024, 11, 20);
            end = LocalDate.of(2024, 11, 30);
            type = AccountTransactionRequestType.ALL;
            page = 0;
            size = 5;

            startTime = LocalDateTime.of(start, LocalTime.MIN);
            endTime = LocalDateTime.of(end, LocalTime.MAX);
            accountTransactionType = type.equals(AccountTransactionRequestType.ALL) ? null : AccountTransactionType.valueOf(type.name());
            pageRequest = PageRequest.of(page, size);

            transactionGetRequest = TransactionGetRequest.builder()
                    .accountNumber(accountNumber)
                    .start(start)
                    .end(end)
                    .type(type)
                    .page(page)
                    .size(size)
                    .build();
        }

        @Test
        void 거래_내역_조회_성공() {
            LocalDateTime startTime = LocalDateTime.of(start, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
            AccountTransactionType accountTransactionType = type.equals(AccountTransactionRequestType.ALL) ? null : AccountTransactionType.valueOf(type.name());

            List<AccountTransaction> accountTransactions = new ArrayList<>();

            for (int i = 1; i <= 10; i++) {
                AccountTransaction accountTransaction = AccountTransactionFixture.builder().id(Long.valueOf(i)).build().accountTransaction();
                accountTransactions.add(accountTransaction);
            }

            Account account = AccountFixture.builder().id(1L).build().account();

            when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
            when(accountTransactionRepository.findAccountTransactionsByCondition(accountNumber, startTime, endTime, accountTransactionType, pageRequest)).thenReturn(accountTransactions);

            List<TransactionGetResponse> transactionList = accountTransactionService.getTransactionList(transactionGetRequest);

            assertEquals(10, transactionList.size());
            for (int i = 0; i < 10; i++) {
                AccountTransaction accountTransaction = accountTransactions.get(i);
                TransactionGetResponse transactionGetResponse = transactionList.get(i);
                assertEquals(accountTransaction.getTitle(), transactionGetResponse.getTitle());
                assertEquals(accountTransaction.getType(), transactionGetResponse.getType());
                assertEquals(accountTransaction.getAmount().longValue(), transactionGetResponse.getAmount());
                assertEquals(accountTransaction.getBalance().longValue(), transactionGetResponse.getBalance());
                assertEquals(accountTransaction.getSender(), transactionGetResponse.getSender());
                assertEquals(accountTransaction.getReceiver(), transactionGetResponse.getReceiver());
                assertEquals(accountTransaction.getCurrencyCode(), transactionGetResponse.getCurrencyCode());
                assertEquals(accountTransaction.getTransactionDate(), transactionGetResponse.getTransactionDate());
            }

            verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
            verify(accountTransactionRepository, times(1)).findAccountTransactionsByCondition(accountNumber, startTime, endTime, accountTransactionType, pageRequest);
        }

        @Test
        void 존재하지_않은_계좌() {
            when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

            BaasException baasException = assertThrows(BaasException.class, () -> accountTransactionService.getTransactionList(transactionGetRequest));

            assertEquals(ErrorCode.ACCOUNT_NOT_FOUND, baasException.getErrorCode());
            assertTrue(baasException.getMessage().equals("계좌 번호: " + accountNumber));

            verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
        }

        @Test
        void 유효하지_않은_계좌() {
            Account account = AccountFixture.builder().id(1L).state(AccountState.INACTIVE).build().account();

            when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));

            BaasException baasException = assertThrows(BaasException.class, () -> accountTransactionService.getTransactionList(transactionGetRequest));

            assertEquals(ErrorCode.INACTIVE_ACCOUNT, baasException.getErrorCode());
            assertTrue(baasException.getMessage().equals("계좌 번호: " + accountNumber));

            verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
        }

        @Test
        void start가_end_보다_미래인_경우() {
            start = LocalDate.of(2024, 12, 31);
            startTime = LocalDateTime.of(start, LocalTime.MIN);
            transactionGetRequest.setStart(start);

            Account account = AccountFixture.builder().id(1L).build().account();

            when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));

            BaasException baasException = assertThrows(BaasException.class, () -> accountTransactionService.getTransactionList(transactionGetRequest));

            assertEquals(ErrorCode.START_IS_AFTER_END, baasException.getErrorCode());
            assertTrue(baasException.getMessage().equals("시작날짜: " + startTime + " 끝날짜: " + endTime));

            verify(accountRepository, times(1)).findByAccountNumber(accountNumber);
        }


     }
}