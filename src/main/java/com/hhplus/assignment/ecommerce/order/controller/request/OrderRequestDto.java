package com.hhplus.assignment.ecommerce.order.controller.request;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record OrderRequestDto(
        Long memberId,
        List<OrderItemRequestDto> orderItemRequestDtos,
        BigDecimal orderPrice
) {

    @Builder
    public record OrderItemRequestDto(
            Long productOptionId,
            Integer quantity
    ) {

    }
}
