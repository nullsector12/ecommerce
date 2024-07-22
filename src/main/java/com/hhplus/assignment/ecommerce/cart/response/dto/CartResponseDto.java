package com.hhplus.assignment.ecommerce.cart.response.dto;

import com.hhplus.assignment.ecommerce.cart.domain.entity.CartEntity;

import java.time.LocalDateTime;

public record CartResponseDto(
        Long memberId,
        Long productOptionId,
        String productName,
        String productOptionName,
        Integer quantity,
        Integer price,
        LocalDateTime createdAt
) {
//    public CartResponseDto(CartEntity cartEntity, String productName, String productOptionName, Integer price) {
//        this(cartEntity.getMemberId(), cartEntity.getProductOptionId(), productName, productOptionName, cartEntity.getQuantity(), cartEntity.getQuantity()*price, cartEntity.getCreatedAt());
//    }
}
