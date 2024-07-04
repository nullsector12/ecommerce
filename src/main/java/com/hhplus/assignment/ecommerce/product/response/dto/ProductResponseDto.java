package com.hhplus.assignment.ecommerce.product.response.dto;

import com.hhplus.assignment.ecommerce.product.domain.entity.ProductEntity;

import java.math.BigDecimal;

public record ProductResponseDto(
        Long id,
        String name,
        BigDecimal price,
        boolean soldOut
) {

    public ProductResponseDto(ProductEntity productEntity, boolean soldOut) {
        this(productEntity.getId(), productEntity.getName(), productEntity.getPrice(), soldOut);
    }
}
