package com.hhplus.assignment.ecommerce.order.domain.repository;

import com.hhplus.assignment.ecommerce.order.controller.request.OrderRequestDto;
import com.hhplus.assignment.ecommerce.order.domain.entity.OrderEntity;
import com.hhplus.assignment.ecommerce.order.service.dto.OrderTopRateDto;

import java.util.List;

public interface OrderRepository {

    List<OrderEntity> getOrderList(long memberId);

    OrderEntity getOrderDetail(long orderId);

    OrderEntity saveOrder(OrderRequestDto orderRequestDto);

    List<OrderTopRateDto> getTopRateOrder();
}
