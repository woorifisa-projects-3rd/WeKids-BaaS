package com.wekids.baas.product.domain;

import com.wekids.baas.common.entity.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString
public class Product extends BaseTime {
    @Id
    private Long id;
}