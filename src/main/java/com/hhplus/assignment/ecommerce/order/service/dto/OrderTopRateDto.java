package com.hhplus.assignment.ecommerce.order.service.dto;

public record OrderTopRateDto(
        Long id,
        Long productOptionId,
        Integer totalOrderCount,
        Integer totalQuantity,
        Integer totalOrderAmount,
        Integer rank
) {
}
