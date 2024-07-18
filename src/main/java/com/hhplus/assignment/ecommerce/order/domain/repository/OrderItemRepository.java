package com.hhplus.assignment.ecommerce.order.domain.repository;

import com.hhplus.assignment.ecommerce.order.domain.entity.OrderItemEntity;
import com.hhplus.assignment.ecommerce.order.service.command.OrderCommand;

import java.util.List;

public interface OrderItemRepository {
    List<OrderItemEntity> getOrderItemList(Long orderId);

    OrderItemEntity saveOrderItem(Long id, OrderCommand.CreateOrder.CreateOrderItem createOrderItem);
}
