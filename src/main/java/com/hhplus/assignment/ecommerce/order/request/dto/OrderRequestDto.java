package com.hhplus.assignment.ecommerce.order.request.dto;

public record OrderRequestDto(
        Long memberId,
        Long productOptionId,
        Integer quantity
) {
}
