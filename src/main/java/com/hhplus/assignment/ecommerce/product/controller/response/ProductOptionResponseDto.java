package com.hhplus.assignment.ecommerce.product.controller.response;

import com.hhplus.assignment.ecommerce.product.domain.entity.ProductOptionEntity;
import com.hhplus.assignment.ecommerce.product.service.command.ProductCommand;

import java.math.BigDecimal;

public record ProductOptionResponseDto(
        Long id,
        Long productId,
        String option,
        BigDecimal optionPrice,
        Integer stock
) {

    public ProductOptionResponseDto(ProductCommand.ProductDetailInfo.ProductOptionInfo productOption) {
        this(productOption.id(), productOption.productId(), productOption.optionName(), productOption.optionPrice()
                , productOption.stock());
    }
}
