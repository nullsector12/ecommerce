package com.hhplus.assignment.ecommerce.order.domain.JpaRepository;

import com.hhplus.assignment.ecommerce.order.domain.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemJpaRepository extends JpaRepository<OrderItemEntity, Long> {
    List<OrderItemEntity> findAllByOrderId(Long orderId);
}
