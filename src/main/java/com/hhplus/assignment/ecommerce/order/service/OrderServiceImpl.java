package com.hhplus.assignment.ecommerce.order.service;

import com.hhplus.assignment.ecommerce.order.controller.request.OrderRequestDto;
import com.hhplus.assignment.ecommerce.order.controller.response.OrderPaymentResponseDto;
import com.hhplus.assignment.ecommerce.order.controller.response.OrderResponseDto;
import com.hhplus.assignment.ecommerce.order.domain.entity.OrderEntity;
import com.hhplus.assignment.ecommerce.order.domain.repository.OrderRepository;
import com.hhplus.assignment.ecommerce.order.service.dto.OrderTopRateDto;
import com.hhplus.assignment.ecommerce.product.service.ProductService;
import com.hhplus.assignment.ecommerce.product.service.dto.ProductOptionDetailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private ProductService productService;

    @Override
    public List<OrderResponseDto> getOrderList(Long memberId) {
        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();
        List<OrderEntity> orderEntities = orderRepository.getOrderList(memberId);
        for(OrderEntity orderEntity : orderEntities) {

            orderResponseDtos.add(new OrderResponseDto(orderEntity,
                    productService.getProductOptionDetail(orderEntity.getProductOptionId())));
        }
        return orderResponseDtos;
    }

    @Override
    public OrderResponseDto getOrderDetail(Long orderId) {
        return new OrderResponseDto(orderRepository.getOrderDetail(orderId), productService.getProductOptionDetail(orderId));
    }

    @Override
    public OrderPaymentResponseDto saveOrder(OrderRequestDto orderRequestDto) {
        ProductOptionDetailDto productOptionDetailDto = productService.getProductOptionDetail(orderRequestDto.productOptionId());
        OrderEntity orderEntity = orderRepository.saveOrder(orderRequestDto);

        return new OrderPaymentResponseDto(orderEntity, productOptionDetailDto);
    }

    @Override
    public List<OrderTopRateDto> getTopRateOrder() {
        return orderRepository.getTopRateOrder();
    }
}
