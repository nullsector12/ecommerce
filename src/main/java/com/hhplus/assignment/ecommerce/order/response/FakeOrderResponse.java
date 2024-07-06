package com.hhplus.assignment.ecommerce.order.response;

import com.hhplus.assignment.ecommerce.order.domain.entity.OrderEntity;
import com.hhplus.assignment.ecommerce.order.response.dto.OrderResponseDto;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductEntity;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductOptionEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class FakeOrderResponse {

    public List<OrderResponseDto> orderResponseDtoList = getOrderResponseDtoList();
    public OrderResponseDto orderResponseDto = orderResponseDtoList.get(0);

    private List<OrderResponseDto> getOrderResponseDtoList() {

        List<OrderEntity> orderEntityList = List.of(
            new OrderEntity(1L, 1L, 1L, "completed", 1, BigDecimal.valueOf(1000), LocalDateTime.now()),
            new OrderEntity(1L, 1L, 1L, "completed", 2, BigDecimal.valueOf(1000), LocalDateTime.now()),
            new OrderEntity(1L, 1L, 1L, "failed", 3, BigDecimal.valueOf(1000), LocalDateTime.now())
        );

        ProductEntity productEntity = new ProductEntity(1L, "product1", BigDecimal.valueOf(1100));
        ProductOptionEntity productOptionEntity = new ProductOptionEntity(1L, 1L, "option1", BigDecimal.valueOf(1000), 7);


        return orderEntityList.stream()
            .map(entity -> new OrderResponseDto(entity, productEntity, productOptionEntity))
            .toList();
    }
}
