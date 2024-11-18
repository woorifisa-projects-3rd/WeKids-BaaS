package com.wekids.baas.support.fixture;

import com.wekids.baas.product.domain.Product;
import com.wekids.baas.product.domain.enums.ProductType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public class ProductFixture {
    @Builder.Default
    private Long id = 5L;
    @Builder.Default
    private ProductType type = ProductType.CHECKING;
    @Builder.Default
    private BigDecimal interestRate = BigDecimal.valueOf(0.1).setScale(6);
    @Builder.Default
    private String name = "우리 아이 행복 통장";
    @Builder.Default
    private LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 0,0, 0);
    @Builder.Default
    private LocalDateTime endDate = LocalDateTime.of(9999, 12, 31, 23, 59, 59);

    public Product product() {
        return Product.builder()
                .id(id)
                .type(type)
                .interestRate(interestRate)
                .name(name)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
