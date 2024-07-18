package com.hhplus.assignment.ecommerce.order.controller.response;

import com.hhplus.assignment.ecommerce.order.service.command.OrderCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class OrderItemResponseDto {
    private Long productId;
    private Long productOptionId;
    private String productName;
    private String productOptionName;
    private Integer quantity;
    private BigDecimal productOptionPrice;

    public OrderItemResponseDto(OrderCommand.OrderHistory.OrderItemInfo orderItemInfo) {
        this.productId = orderItemInfo.productId();
        this.productOptionId = orderItemInfo.productOptionId();
        this.productName = orderItemInfo.productName();
        this.productOptionName = orderItemInfo.productOptionName();
        this.quantity = orderItemInfo.quantity();
        this.productOptionPrice = orderItemInfo.productOptionPrice();
    }

}
