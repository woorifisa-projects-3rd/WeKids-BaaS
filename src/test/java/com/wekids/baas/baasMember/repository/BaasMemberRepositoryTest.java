package com.wekids.baas.baasMember.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BaasMemberRepositoryTest {
    @Autowired
    BaasMemberRepository baasMemberRepository;

    @Test
    void test() {
        baasMemberRepository.findAll();
    }

}