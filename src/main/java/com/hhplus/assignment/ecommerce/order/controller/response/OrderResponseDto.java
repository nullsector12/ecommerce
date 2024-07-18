package com.hhplus.assignment.ecommerce.order.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hhplus.assignment.ecommerce.order.service.command.OrderCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderResponseDto {

    private Long orderId;
    private Long memberId;
    private List<OrderItemResponseDto> orderItems;
    private BigDecimal totalPrice;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderedAt;

    public OrderResponseDto(OrderCommand.OrderHistory orderHistory) {
        this.orderId = orderHistory.orderId();
        this.memberId = orderHistory.memberId();
        this.orderItems = orderHistory.orderItems().stream().map(OrderItemResponseDto::new).toList();
        this.totalPrice = orderHistory.totalPrice();
        this.orderedAt = orderHistory.orderedAt();
    }
}
