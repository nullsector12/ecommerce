package com.hhplus.assignment.ecommerce.product.response.dto;

import com.hhplus.assignment.ecommerce.product.domain.entity.ProductEntity;

public record TopSalesProductResponseDto(
        Long productId,
        String productName,
        Integer soldQuantity,
        Integer rank
) {
    public TopSalesProductResponseDto(ProductEntity product, Integer soldQuantity, Integer rank) {
        this(product.getId(), product.getName(), soldQuantity, rank);
    }
}
