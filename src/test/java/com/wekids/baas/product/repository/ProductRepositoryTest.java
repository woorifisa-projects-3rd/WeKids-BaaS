package com.wekids.baas.product.repository;

import com.wekids.baas.product.domain.Product;
import com.wekids.baas.support.fixture.ProductFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @Test
    void findById() {
        Long id = 5L;
        Product answer = ProductFixture.builder().build().product();

        Product product = productRepository.findById(id).get();

        assertThat(product.getId()).isEqualTo(answer.getId());
        assertThat(product.getType()).isEqualTo(answer.getType());
        assertThat(product.getInterestRate()).isEqualTo(answer.getInterestRate());
        assertThat(product.getName()).isEqualTo(answer.getName());
        assertThat(product.getStartDate()).isEqualTo(answer.getStartDate());
        assertThat(product.getEndDate()).isEqualTo(answer.getEndDate());
    }

}