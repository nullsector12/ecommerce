package com.hhplus.assignment.ecommerce.product.controller.response;

import com.hhplus.assignment.ecommerce.product.domain.entity.ProductEntity;
import com.hhplus.assignment.ecommerce.product.service.command.ProductCommand;

import java.math.BigDecimal;

public record ProductResponseDto(
        Long id,
        String name,
        BigDecimal price,
        boolean soldOut
) {

    public ProductResponseDto(ProductCommand.ProductInfo productInfo) {
        this(productInfo.id(), productInfo.name(), productInfo.price(), productInfo.isSoldOut());
    }
}
