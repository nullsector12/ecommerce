package com.hhplus.assignment.ecommerce.product.response.dto;

import com.hhplus.assignment.ecommerce.product.domain.entity.ProductOptionEntity;

import java.math.BigDecimal;

public record ProductOptionResponseDto(
        Long id,
        Long productId,
        String option,
        BigDecimal optionPrice,
        Integer stock
) {

    public ProductOptionResponseDto(ProductOptionEntity productOptionEntity) {
        this(productOptionEntity.getId(), productOptionEntity.getProductId(), productOptionEntity.getOption(), productOptionEntity.getOptionPrice(), productOptionEntity.getStock());
    }
}
