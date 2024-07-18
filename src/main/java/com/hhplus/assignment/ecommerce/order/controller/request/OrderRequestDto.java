package com.hhplus.assignment.ecommerce.order.controller.request;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequestDto(
        Long memberId,
        List<OrderItemRequestDto> orderItemRequestDtos,
        BigDecimal orderPrice
) {

    public record OrderItemRequestDto(
            Long productOptionId,
            Integer quantity
    ) {

    }
}
