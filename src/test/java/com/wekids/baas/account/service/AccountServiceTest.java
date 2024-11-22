package com.wekids.baas.account.service;

import com.wekids.baas.account.domain.Account;
import com.wekids.baas.account.dto.request.AccountCreateRequest;
import com.wekids.baas.account.dto.response.AccountCreateResponse;
import com.wekids.baas.account.dto.response.MemberAccountGetResponse;
import com.wekids.baas.account.repository.AccountRepository;
import com.wekids.baas.bankMember.domain.BankMember;
import com.wekids.baas.bankMember.repository.BankMemberRepository;
import com.wekids.baas.exception.BaasException;
import com.wekids.baas.exception.ErrorCode;
import com.wekids.baas.product.domain.Product;
import com.wekids.baas.product.domain.enums.ProductType;
import com.wekids.baas.product.repository.ProductRepository;
import com.wekids.baas.support.fixture.AccountFixture;
import com.wekids.baas.support.fixture.BankMemberFixture;
import com.wekids.baas.support.fixture.ProductFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @InjectMocks
    AccountServiceImpl accountService;
    @Mock
    AccountRepository accountRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    BankMemberRepository bankMemberRepository;

    @Nested
    class AccountCreateTest {
        Long bankMemberId;
        Long productId;
        String password;
        AccountCreateRequest accountCreateRequest;

        @BeforeEach
        void setUp() {
            bankMemberId = 1L;
            productId = 5L;
            password = "1234";

            accountCreateRequest = AccountCreateRequest.builder()
                    .bankMemberId(bankMemberId)
                    .productId(productId)
                    .password(password)
                    .build();
        }
        @Test
        void 계좌_생성_성공() {
            BankMember bankMember = BankMemberFixture.builder().build().bankMember();
            Product product = ProductFixture.builder().build().product();
            Account account = AccountFixture.builder().id(1L).build().account();

            when(bankMemberRepository.findById(bankMemberId)).thenReturn(Optional.of(bankMember));
            when(productRepository.findById(productId)).thenReturn(Optional.of(product));
            when(accountRepository.save(any(Account.class))).thenReturn(account);

            AccountCreateResponse accountCreateResponse = accountService.createAccount(accountCreateRequest);

            assertNotNull(accountCreateResponse);
            assertEquals(account.getAccountNumber(), accountCreateResponse.getAccountNumber());
            assertEquals(account.getProduct().getType().name(), accountCreateResponse.getType());
            assertEquals(account.getExpireDate(), accountCreateResponse.getExpireDate());

            verify(bankMemberRepository, times(1)).findById(bankMemberId);
            verify(productRepository, times(1)).findById(productId);
            verify(accountRepository, times(1)).save(any(Account.class));
        }

        @Test
        void 은행_고객을_찾을_수_없는_경우() {
            when(bankMemberRepository.findById(bankMemberId)).thenReturn(Optional.empty());

            BaasException baasException = assertThrows(BaasException.class, () -> accountService.createAccount(accountCreateRequest));
            assertEquals(ErrorCode.BANK_MEMBER_NOT_FOUND, baasException.getErrorCode());
            assertTrue(baasException.getMessage().equals("은행 고객 아이디: " + bankMemberId));

            verify(bankMemberRepository, times(1)).findById(bankMemberId);
        }

        @Test
        void 상품을_찾을_수_없는_경우() {
            BankMember bankMember = BankMemberFixture.builder().build().bankMember();

            when(bankMemberRepository.findById(bankMemberId)).thenReturn(Optional.of(bankMember));
            when(productRepository.findById(productId)).thenReturn(Optional.empty());

            BaasException baasException = assertThrows(BaasException.class, () -> accountService.createAccount(accountCreateRequest));
            assertEquals(ErrorCode.PRODUCT_NOT_FOUND, baasException.getErrorCode());
            assertTrue(baasException.getMessage().equals("계좌 상품 아이디: " + productId));

            verify(productRepository, times(1)).findById(productId);
        }
    }

    @Nested
    class MemberAccountListGetTest {
        Long baasMemberId;
        Long bankMemberId;

        @ParameterizedTest
        @CsvSource({"1, 1, 1", "5, 10, 2", "2, 3, 0"})
        void 고객_계좌_리스트_조회_성공(Long baasMemberId, Long bankMemberId, Long size) {
            this. bankMemberId = bankMemberId;
            this.baasMemberId = baasMemberId;

            List<Account> accounts = new ArrayList<>();

            for (int i = 1; i <= size; i++) {
                Account account = AccountFixture.builder().id(Long.valueOf(i)).build().account();
                accounts.add(account);
            }

            when(accountRepository.findAccountsByBankMemberIdAndBaasMemberId(bankMemberId, baasMemberId)).thenReturn(accounts);

            List<MemberAccountGetResponse> memberAccountList = accountService.getMemberAccountList(baasMemberId, bankMemberId);

            assertNotNull(memberAccountList);
            assertEquals(size, memberAccountList.size());

            verify(accountRepository, times(1)).findAccountsByBankMemberIdAndBaasMemberId(bankMemberId, baasMemberId);
        }
    }





}