package com.hhplus.assignment.ecommerce.product.controller.response;

import com.hhplus.assignment.ecommerce.product.domain.entity.ProductEntity;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductOptionEntity;
import com.hhplus.assignment.ecommerce.product.service.command.ProductCommand;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public record  ProductDetailResponseDto(
        Long id,
        String name,
        BigDecimal price,
        List<ProductOptionResponseDto> options
) {

    public ProductDetailResponseDto(ProductCommand.ProductDetailInfo productDetailInfo) {
        this(productDetailInfo.id(), productDetailInfo.name(), productDetailInfo.price(),
                productDetailInfo.options().stream().map(ProductOptionResponseDto::new).collect(Collectors.toList()));
    }
}
