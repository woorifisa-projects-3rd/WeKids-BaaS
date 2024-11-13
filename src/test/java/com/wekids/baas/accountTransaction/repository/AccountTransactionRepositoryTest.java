package com.wekids.baas.accountTransaction.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountTransactionRepositoryTest {
    @Autowired
    AccountTransactionRepository accountTransactionRepository;

    @Test
    void test() {
        accountTransactionRepository.findAll();
    }
}