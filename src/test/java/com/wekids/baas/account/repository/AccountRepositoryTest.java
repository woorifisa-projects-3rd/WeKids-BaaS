package com.wekids.baas.account.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;

    @Test
    void test() {
        accountRepository.findAll();
    }

}