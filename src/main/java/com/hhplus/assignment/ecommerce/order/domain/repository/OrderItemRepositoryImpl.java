package com.hhplus.assignment.ecommerce.order.domain.repository;

import com.hhplus.assignment.ecommerce.order.domain.JpaRepository.OrderItemJpaRepository;
import com.hhplus.assignment.ecommerce.order.domain.entity.OrderItemEntity;
import com.hhplus.assignment.ecommerce.order.service.command.OrderCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepository {

    private final OrderItemJpaRepository orderItemJpaRepository;

    @Override
    public List<OrderItemEntity> getOrderItemList(Long orderId) {
        return orderItemJpaRepository.findAllByOrderId(orderId);
    }

    @Override
    public OrderItemEntity saveOrderItem(Long id, OrderCommand.CreateOrder.CreateOrderItem createOrderItem) {
        return orderItemJpaRepository.save(OrderItemEntity.builder()
            .orderId(id)
            .productId(createOrderItem.productId())
            .productOptionId(createOrderItem.productOptionId())
            .quantity(createOrderItem.quantity())
            .productName(createOrderItem.productName())
            .productOptionName(createOrderItem.productOptionName())
            .productOptionPrice(createOrderItem.productOptionPrice())
            .build());
    }
}
