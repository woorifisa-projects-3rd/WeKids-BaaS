package com.wekids.baas.accountTransaction.repository;

import com.wekids.baas.accountTransaction.domain.AccountTransaction;
import com.wekids.baas.accountTransaction.domain.enums.AccountTransactionType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountTransactionRepositoryTest {
    @Autowired
    AccountTransactionRepository accountTransactionRepository;

    @Test
    void findAccountTransactionByCondition() {
        LocalDateTime start = LocalDateTime.of(2024, 11, 20, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 11, 25, 23, 59, 59);

        PageRequest pageRequest = PageRequest.of(1, 3);

        List<AccountTransaction> transactions = accountTransactionRepository.findAccountTransactionsByCondition("1002913023908", start, end, AccountTransactionType.WITHDRAW, pageRequest);

        System.out.println("transactions = " + transactions);

        Assertions.assertThat(transactions.size()).isEqualTo(3);
    }
}