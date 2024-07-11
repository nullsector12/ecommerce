package com.hhplus.assignment.ecommerce.product.service.dto;

import com.hhplus.assignment.ecommerce.product.domain.entity.ProductEntity;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductOptionEntity;

public record ProductOptionDetailDto(
        Long id,
        Long productId,
        String productName,
        String productOptionName,
        Integer stock
) {
    public ProductOptionDetailDto(ProductEntity product, ProductOptionEntity productOption){
        this(productOption.getId(), product.getId(), product.getName(), productOption.getOption(), productOption.getStock());
    }
}
