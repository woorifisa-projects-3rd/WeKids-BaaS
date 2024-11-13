package com.wekids.baas.bankMember.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BankMemberRepositoryTest {
    @Autowired
    BankMemberRepository bankMemberRepository;

    @Test
    void test() {
        bankMemberRepository.findAll();
    }

}