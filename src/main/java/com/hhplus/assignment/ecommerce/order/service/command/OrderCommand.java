package com.hhplus.assignment.ecommerce.order.service.command;

import com.hhplus.assignment.ecommerce.order.controller.request.OrderRequestDto;
import com.hhplus.assignment.ecommerce.order.domain.entity.OrderEntity;
import com.hhplus.assignment.ecommerce.order.domain.entity.OrderItemEntity;
import com.hhplus.assignment.ecommerce.order.service.dto.TopOrderedProductInterface;
import com.hhplus.assignment.ecommerce.product.service.command.ProductCommand;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderCommand {

    public record OrderHistory(
            Long orderId,
            Long memberId,
            List<OrderItemInfo> orderItems,
            BigDecimal totalPrice,
            LocalDateTime orderedAt
    ) {
        public OrderHistory(OrderEntity order, List<OrderItemEntity> orderItems) {
            this(order.getId(), order.getMemberId(),
                    orderItems.stream().map(OrderItemInfo::new).toList(), order.getTotalPrice(), order.getOrderAt());
        }
        public record OrderItemInfo(
                Long productId,
                Long productOptionId,
                Integer quantity,
                String productName,
                String productOptionName,
                BigDecimal productOptionPrice
        ) {
            public OrderItemInfo(OrderItemEntity orderItem) {
                this(orderItem.getProductId(), orderItem.getProductOptionId(), orderItem.getQuantity(), orderItem.getProductName(),
                        orderItem.getProductOptionName(), orderItem.getProductOptionPrice());

            }
        }
    }

    @Builder
    public record CreateOrder (
            Long memberId,
            List<CreateOrderItem> orderItems,
            BigDecimal totalPrice
    ) {

        @Builder
        public record CreateOrderItem(
                Long productId,
                Long productOptionId,
                Integer quantity,
                String productName,
                String productOptionName,
                BigDecimal productOptionPrice
        ) {
        }
    }

    public record TopOrderedProduct(
            Long productId,
            Long totalQuantity,
            Long totalOrderCount,
            Long totalOrderAmount
    ) {
        public TopOrderedProduct(TopOrderedProductInterface topOrderedProduct) {
            this(topOrderedProduct.getProductId(), topOrderedProduct.getTotalQuantity(), topOrderedProduct.getTotalOrderCount(), topOrderedProduct.getTotalOrderAmount());
        }
    }
}
