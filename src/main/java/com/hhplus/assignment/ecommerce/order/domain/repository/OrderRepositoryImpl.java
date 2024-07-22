package com.hhplus.assignment.ecommerce.order.domain.repository;

import com.hhplus.assignment.ecommerce.order.domain.JpaRepository.OrderJpaRepository;
import com.hhplus.assignment.ecommerce.order.domain.entity.OrderEntity;
import com.hhplus.assignment.ecommerce.order.service.command.OrderCommand;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Order;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public List<OrderEntity> getOrderList(long memberId) {
        return orderJpaRepository.findAllByMemberId(memberId);
    }

    @Override
    public OrderEntity getOrderDetail(long orderId) {
        return orderJpaRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 정보가 없습니다."));
    }

    @Override
    public OrderEntity saveOrder(OrderCommand.CreateOrder createOrder) {
        return orderJpaRepository.save(OrderEntity.builder()
            .memberId(createOrder.memberId())
            .status("ORDERED")
            .totalPrice(createOrder.totalPrice())
            .build());
    }

    @Override
    public List<OrderCommand.TopOrderedProduct> getTopRateOrder() {
        return orderJpaRepository.findTopOrderProduct(LocalDateTime.now().minusDays(3)).stream()
                .map(OrderCommand.TopOrderedProduct::new).toList();
    }
}
