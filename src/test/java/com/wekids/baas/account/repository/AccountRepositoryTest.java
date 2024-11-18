package com.wekids.baas.account.repository;

import com.wekids.baas.account.domain.Account;
import com.wekids.baas.support.fixture.AccountFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;

    @Test
    void saveAccount() {
        Account account = AccountFixture.builder().build().account();

        Account savedAccount = accountRepository.save(account);

        System.out.println("savedAccount = " + savedAccount);

        assertThat(savedAccount.getAccountNumber()).isEqualTo(account.getAccountNumber());
        assertThat(savedAccount.getBalance()).isEqualTo(account.getBalance());
        assertThat(savedAccount.getPassword()).isEqualTo(account.getPassword());
        assertThat(savedAccount.getExpireDate()).isEqualTo(account.getExpireDate());
        assertThat(savedAccount.getInactiveDate()).isEqualTo(account.getInactiveDate());
        assertThat(savedAccount.getState()).isEqualTo(account.getState());
        assertThat(savedAccount.getProduct()).isEqualTo(account.getProduct());
        assertThat(savedAccount.getBankMember()).isEqualTo(account.getBankMember());
        assertThat(savedAccount.getBankCode()).isEqualTo(account.getBankCode());
    }

}