package com.hhplus.assignment.ecommerce.order.service;

import com.hhplus.assignment.ecommerce.order.controller.request.OrderRequestDto;
import com.hhplus.assignment.ecommerce.order.controller.response.OrderPaymentResponseDto;
import com.hhplus.assignment.ecommerce.order.controller.response.OrderResponseDto;
import com.hhplus.assignment.ecommerce.order.service.dto.OrderTopRateDto;

import java.util.List;

public interface OrderService {

    // 주문 목록 조회
    List<OrderResponseDto> getOrderList(Long memberId);

    // 주문 상세 조회
    OrderResponseDto getOrderDetail(Long orderId);

    // 주문
    OrderPaymentResponseDto saveOrder(OrderRequestDto orderRequestDto);

    // 주문 상위 조회
    List<OrderTopRateDto> getTopRateOrder();
}
