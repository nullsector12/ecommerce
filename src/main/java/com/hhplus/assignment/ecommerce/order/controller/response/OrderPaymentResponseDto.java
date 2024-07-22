package com.hhplus.assignment.ecommerce.order.controller.response;

import com.hhplus.assignment.ecommerce.order.service.command.OrderCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderPaymentResponseDto{
    private Long orderId;
    private Long memberId;
    private List<OrderPaymentItemResponseDto> orderItems;
    private BigDecimal totalPrice;
    private LocalDateTime orderedAt;

    public record OrderPaymentItemResponseDto(
            Long productId,
            Long productOptionId,
            Integer quantity,
            String productName,
            String productOptionName,
            BigDecimal productOptionPrice
    ) {
        public OrderPaymentItemResponseDto (OrderCommand.OrderHistory.OrderItemInfo orderItemInfo) {
            this(orderItemInfo.productId(), orderItemInfo.productOptionId(), orderItemInfo.quantity(), orderItemInfo.productName(),
                    orderItemInfo.productOptionName(), orderItemInfo.productOptionPrice());
        }
    }

    public OrderPaymentResponseDto(OrderCommand.OrderHistory orderHistory) {
        this.orderId = orderHistory.orderId();
        this.memberId = orderHistory.memberId();
        this.orderItems = orderHistory.orderItems().stream().map(OrderPaymentItemResponseDto::new).toList();
        this.totalPrice = orderHistory.totalPrice();
        this.orderedAt = orderHistory.orderedAt();
    }
}
