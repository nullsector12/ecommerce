package com.hhplus.assignment.ecommerce.order.response.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhplus.assignment.ecommerce.order.domain.entity.OrderEntity;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductEntity;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductOptionEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponseDto (
        Long orderId,
        Long productOptionId,
        Long memberId,
        String productName,
        String productOptionName,
        String orderStatus,
        Integer orderQuantity,
        BigDecimal orderPrice,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime orderAt
) {

    public OrderResponseDto(OrderEntity orderEntity, ProductEntity productEntity, ProductOptionEntity productOptionEntity) {
        this(orderEntity.getId(), orderEntity.getProductOptionId(), orderEntity.getMemberId(), productEntity.getName()
                , productOptionEntity.getOption(), orderEntity.getStatus(), orderEntity.getQuantity()
                , orderEntity.getOrderPrice(), orderEntity.getOrderAt());
    }
}
