package com.wekids.baas.card.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CardRepositoryTest {
    @Autowired
    CardRepository cardRepository;

    @Test
    void test() {
        cardRepository.findAll();
    }

}