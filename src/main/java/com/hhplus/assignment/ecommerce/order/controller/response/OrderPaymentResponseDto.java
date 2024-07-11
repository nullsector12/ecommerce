package com.hhplus.assignment.ecommerce.order.controller.response;

import com.hhplus.assignment.ecommerce.order.domain.entity.OrderEntity;
import com.hhplus.assignment.ecommerce.product.service.dto.ProductOptionDetailDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderPaymentResponseDto(
        Long orderId,
        Long memberId,
        Long productOptionId,
        Integer orderQuantity,
        BigDecimal orderPrice,
        String orderStatus,
        String productName,
        String productOptionName,
        LocalDateTime orderAt
) {
    public OrderPaymentResponseDto(OrderEntity order, ProductOptionDetailDto productOptionDetail) {
        this(order.getId(), order.getMemberId(), order.getProductOptionId(), order.getQuantity(), order.getOrderPrice()
                , order.getStatus(), productOptionDetail.productName(), productOptionDetail.productOptionName()
                , order.getOrderAt());
    }
}
