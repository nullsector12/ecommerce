package com.hhplus.assignment.ecommerce.order.domain.repository;

import com.hhplus.assignment.ecommerce.order.domain.entity.OrderEntity;
import com.hhplus.assignment.ecommerce.order.service.command.OrderCommand;
import com.hhplus.assignment.ecommerce.order.service.dto.TopOrderedProduct;

import java.util.List;

public interface OrderRepository {

    List<OrderEntity> getOrderList(long memberId);

    OrderEntity getOrderDetail(long orderId);

    OrderEntity saveOrder(OrderCommand.CreateOrder createOrder);

    List<OrderCommand.TopOrderedProduct> getTopRateOrder();
}
