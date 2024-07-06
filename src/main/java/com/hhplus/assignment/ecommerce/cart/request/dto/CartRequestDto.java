package com.hhplus.assignment.ecommerce.cart.request.dto;

public record CartRequestDto(
        Long memberId,
        Long cartId,
        Long productOptionId,
        Integer quantity,
        String status
) {
}
