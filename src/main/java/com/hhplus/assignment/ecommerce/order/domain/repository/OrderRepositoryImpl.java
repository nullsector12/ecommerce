package com.hhplus.assignment.ecommerce.order.domain.repository;

import com.hhplus.assignment.ecommerce.order.controller.request.OrderRequestDto;
import com.hhplus.assignment.ecommerce.order.domain.JpaRepository.OrderJpaRepository;
import com.hhplus.assignment.ecommerce.order.domain.entity.OrderEntity;
import com.hhplus.assignment.ecommerce.order.service.dto.OrderTopRateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public OrderEntity saveOrder(OrderRequestDto orderRequestDto) {
        return orderJpaRepository.save(OrderEntity.builder()
            .productOptionId(orderRequestDto.productOptionId())
            .memberId(orderRequestDto.memberId())
            .status("ORDERED")
            .quantity(orderRequestDto.quantity())
            .orderPrice(orderRequestDto.orderPrice())
            .build());
    }

    @Override
    public List<OrderTopRateDto> getTopRateOrder() {
        List<OrderEntity> orderEntities = orderJpaRepository.findAllByStatusAndOrderAt("ORDERED", LocalDateTime.now().minusDays(2));
        List<OrderTopRateDto> orderTopRates = new ArrayList<>();
        int rank = 1;
        for(OrderEntity orderEntity : orderEntities) {
            List<OrderEntity> topRateOrder = orderJpaRepository.findAllByIdAndStatus(orderEntity.getId(), "ORDERED");
            orderTopRates.add(
                    new OrderTopRateDto(
                            orderEntity.getId(),
                            orderEntity.getProductOptionId(),
                            topRateOrder.size(),
                            topRateOrder.stream().mapToInt(OrderEntity::getQuantity).sum(),
                            topRateOrder.stream().map(OrderEntity::getOrderPrice)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add).intValue(),
                            rank
                    )
            );
            rank++;
        }

        return orderTopRates;
    }
}
