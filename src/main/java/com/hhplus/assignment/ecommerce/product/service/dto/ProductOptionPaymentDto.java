package com.hhplus.assignment.ecommerce.product.service.dto;

import com.hhplus.assignment.ecommerce.product.domain.entity.ProductOptionEntity;

public record ProductOptionPaymentDto(
        Long id,
        Long productId,
        String productOptionName,
        Integer stock
) {
    public ProductOptionPaymentDto(ProductOptionEntity productOptionEntity) {
        this(productOptionEntity.getId(), productOptionEntity.getProductId(), productOptionEntity.getOption(), productOptionEntity.getStock());
    }
}
