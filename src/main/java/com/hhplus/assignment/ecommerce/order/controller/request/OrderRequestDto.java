package com.hhplus.assignment.ecommerce.order.controller.request;

import java.math.BigDecimal;

public record OrderRequestDto(
        Long memberId,
        Long productOptionId,
        Integer quantity,
        BigDecimal orderPrice
) {
}
