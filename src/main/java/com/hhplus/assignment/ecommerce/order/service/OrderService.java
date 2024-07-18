package com.hhplus.assignment.ecommerce.order.service;

import com.hhplus.assignment.ecommerce.order.domain.entity.OrderEntity;
import com.hhplus.assignment.ecommerce.order.domain.entity.OrderItemEntity;
import com.hhplus.assignment.ecommerce.order.domain.repository.OrderItemRepository;
import com.hhplus.assignment.ecommerce.order.domain.repository.OrderRepository;
import com.hhplus.assignment.ecommerce.order.service.command.OrderCommand;
import com.hhplus.assignment.ecommerce.order.service.dto.TopOrderedProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public List<OrderCommand.OrderHistory> getOrderList(Long memberId) {
        List<OrderCommand.OrderHistory> result = new ArrayList<>();
        List<OrderEntity> orderEntity = orderRepository.getOrderList(memberId);
        for(OrderEntity order : orderEntity) {
            List<OrderItemEntity> orderItemEntities = orderItemRepository.getOrderItemList(order.getId());
            result.add(new OrderCommand.OrderHistory(order, orderItemEntities));
        }
        return result;
    }

    public OrderCommand.OrderHistory getOrderDetail(Long orderId) {
        OrderEntity orderEntity = orderRepository.getOrderDetail(orderId);
        List<OrderItemEntity> orderItemEntities = orderItemRepository.getOrderItemList(orderEntity.getId());
        return new OrderCommand.OrderHistory(orderEntity, orderItemEntities);
    }

    @Transactional
    public OrderCommand.OrderHistory saveOrder(OrderCommand.CreateOrder createOrder) {
        OrderEntity orderEntity = orderRepository.saveOrder(createOrder);
        List<OrderItemEntity> orderItemEntities = new ArrayList<>();
        for(OrderCommand.CreateOrder.CreateOrderItem createOrderItem : createOrder.orderItems()) {
            orderItemEntities.add(orderItemRepository.saveOrderItem(orderEntity.getId(), createOrderItem));
        }
        return null;
    }

    public List<OrderCommand.TopOrderedProduct> getTopRateOrder() {
        return orderRepository.getTopRateOrder();
    }


}
