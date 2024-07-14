package com.hhplus.assignment.ecommerce.product.controller.response;

import com.hhplus.assignment.ecommerce.order.service.dto.OrderTopRateDto;
import com.hhplus.assignment.ecommerce.product.service.dto.ProductOptionDetailDto;

public record TopSalesProductResponseDto(
        Long productId,
        String productName,
        Integer totalOrderCount,
        Integer soldQuantity,
        Integer rank
) {
    public TopSalesProductResponseDto(ProductOptionDetailDto product, OrderTopRateDto orderTopRateDto) {
        this(product.productId(), product.productName(), orderTopRateDto.totalOrderCount(), orderTopRateDto.totalQuantity(), orderTopRateDto.rank());
    }
}
