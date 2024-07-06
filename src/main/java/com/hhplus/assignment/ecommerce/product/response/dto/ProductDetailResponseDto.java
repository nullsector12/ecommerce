package com.hhplus.assignment.ecommerce.product.response.dto;

import com.hhplus.assignment.ecommerce.product.domain.entity.ProductEntity;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductOptionEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public record ProductDetailResponseDto(
        Long id,
        String name,
        BigDecimal price,
        List<ProductOptionResponseDto> options
) {

    public ProductDetailResponseDto(ProductEntity productEntity, List<ProductOptionEntity> productOptionEntities) {
        this(productEntity.getId(), productEntity.getName(), productEntity.getPrice(), productOptionEntities.stream()
                .map(ProductOptionResponseDto::new)
                .collect(Collectors.toList()));
    }
}
