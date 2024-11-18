package com.wekids.baas.product.domain;

import com.wekids.baas.common.entity.BaseTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
public class Product extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 8, scale = 6, nullable = false)
    private BigDecimal interestRate;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;
}