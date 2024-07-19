package com.hhplus.assignment.ecommerce.order.controller;

import com.hhplus.assignment.ecommerce.common.genericResponse.DataResponse;
import com.hhplus.assignment.ecommerce.common.genericResponse.GenericResponse;
import com.hhplus.assignment.ecommerce.exception.EcommerceException;
import com.hhplus.assignment.ecommerce.order.controller.request.OrderRequestDto;
import com.hhplus.assignment.ecommerce.order.controller.response.OrderListResponse;
import com.hhplus.assignment.ecommerce.order.controller.response.OrderPaymentResponseDto;
import com.hhplus.assignment.ecommerce.order.controller.response.OrderResponseDto;
import com.hhplus.assignment.ecommerce.order.facade.OrderFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "주문/결제", description = "주문/결제 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderFacade orderFacade;

    @Operation(summary = "주문 내역 조회", description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공",
                            content = @Content(schema = @Schema(implementation = OrderListResponse.class)))
            })
    // 주문 목록 조회
    @GetMapping("/{memberId}")
    public GenericResponse getOrderList(@PathVariable("memberId") Long memberId) {
        return OrderListResponse.create(orderFacade.getOrderList(memberId));
    }

    @Operation(summary = "주문 상세 조회", description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공",
                            content = @Content(schema = @Schema(implementation = OrderResponseDto.class)))
            })
    // 주문 상세 조회
    @GetMapping("/detail/{orderId}")
    public GenericResponse getOrderDetail(@PathVariable("orderId") Long orderId) {
        return DataResponse.create(orderFacade.getOrderDetail(orderId));
    }

    @Operation(summary = "주문/결제", description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공",
                            content = @Content(schema = @Schema(implementation = OrderPaymentResponseDto.class)))
            })
    // 주문/결제
    @PostMapping
    public GenericResponse orderPayment(@RequestBody OrderRequestDto requestDto) {
        return DataResponse.create(orderFacade.paymentOrder(requestDto));
    }


}
