package com.hhplus.assignment.ecommerce.order.controller;

import com.hhplus.assignment.ecommerce.common.genericResponse.DataResponse;
import com.hhplus.assignment.ecommerce.common.genericResponse.GenericResponse;
import com.hhplus.assignment.ecommerce.order.request.dto.OrderRequestDto;
import com.hhplus.assignment.ecommerce.order.response.FakeOrderResponse;
import com.hhplus.assignment.ecommerce.order.response.OrderListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final FakeOrderResponse fakeOrderResponse;

    // 주문 목록 조회
    @GetMapping
    public GenericResponse getOrderList() {
        return OrderListResponse.create(fakeOrderResponse.orderResponseDtoList);
    }

    // 주문 상세 조회
    @GetMapping("/{orderId}")
    public GenericResponse getOrderDetail(@PathVariable("orderId") Long orderId) {
        return DataResponse.create(fakeOrderResponse.orderResponseDto);
    }

    // 주문/결제
    @PostMapping
    public GenericResponse orderPayment(@RequestBody OrderRequestDto requestDto) {
        return DataResponse.create(fakeOrderResponse.orderResponseDto);
    }


}
