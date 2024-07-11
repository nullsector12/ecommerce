package com.hhplus.assignment.ecommerce.order.service;

import com.hhplus.assignment.ecommerce.order.domain.entity.OrderEntity;
import com.hhplus.assignment.ecommerce.order.controller.response.OrderResponseDto;
import com.hhplus.assignment.ecommerce.order.domain.repository.OrderRepository;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductEntity;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductOptionEntity;
import com.hhplus.assignment.ecommerce.product.service.ProductService;
import com.hhplus.assignment.ecommerce.product.service.dto.ProductOptionDetailDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderServiceImpl orderService;

    private List<OrderEntity> orderEntityList;
    private ProductOptionDetailDto productOptionDetailDto;

    @BeforeEach
    void setUp() {
        orderEntityList = List.of(
                new OrderEntity(1L, 1L, 1L, "ordered", 2, new BigDecimal(8000), LocalDateTime.now())
        );

        productOptionDetailDto = new ProductOptionDetailDto(
                ProductEntity.builder()
                        .id(1L)
                        .name("product1")
                        .price(new BigDecimal(12000000))
                        .build(),
                ProductOptionEntity.builder()
                        .id(1L)
                        .productId(1L)
                        .optionPrice(new BigDecimal(4000))
                        .option("option1")
                        .stock(8)
                        .build());
    }

    @Test
    @DisplayName("주문 목록 조회")
    void getOrderList() {
        // given
        long memberId = 1L;
        when(orderRepository.getOrderList(memberId)).thenReturn(orderEntityList);
        when(productService.getProductOptionDetail(orderEntityList.get(0).getProductOptionId())).thenReturn(productOptionDetailDto);

        // when
        List<OrderResponseDto> orderResponseDtoList = orderService.getOrderList(memberId);

        // then
        OrderEntity orderEntity = orderEntityList.get(0);
        OrderResponseDto orderResponseDto = orderResponseDtoList.get(0);
        assertEquals(orderEntity.getId(), orderResponseDto.orderId());
        assertEquals(orderEntity.getMemberId(), orderResponseDto.memberId());
        assertEquals(orderEntity.getProductOptionId(), orderResponseDto.productOptionId());
        assertEquals(orderEntity.getStatus(), orderResponseDto.orderStatus());
        assertEquals(orderEntity.getQuantity(), orderResponseDto.orderQuantity());
        assertEquals(orderEntity.getOrderPrice(), orderResponseDto.orderPrice());
        assertEquals(orderEntity.getOrderAt(), orderResponseDto.orderAt());

    }

    @Test
    @DisplayName("주문 상세 조회")
    void getOrderDetail() {
        // given
        long orderId = 1L;
        when(orderRepository.getOrderDetail(orderId)).thenReturn(orderEntityList.get(0));
        when(productService.getProductOptionDetail(orderEntityList.get(0).getProductOptionId())).thenReturn(productOptionDetailDto);

        // when
        OrderResponseDto orderResponseDto = orderService.getOrderDetail(orderId);

        // then
        OrderEntity orderEntity = orderEntityList.get(0);
        assertEquals(orderEntity.getId(), orderResponseDto.orderId());
        assertEquals(orderEntity.getMemberId(), orderResponseDto.memberId());
        assertEquals(orderEntity.getProductOptionId(), orderResponseDto.productOptionId());
        assertEquals(orderEntity.getStatus(), orderResponseDto.orderStatus());
        assertEquals(orderEntity.getQuantity(), orderResponseDto.orderQuantity());
        assertEquals(orderEntity.getOrderPrice(), orderResponseDto.orderPrice());
        assertEquals(orderEntity.getOrderAt(), orderResponseDto.orderAt());
    }
}
