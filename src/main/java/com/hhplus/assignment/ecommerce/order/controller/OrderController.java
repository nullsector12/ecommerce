package com.hhplus.assignment.ecommerce.order.controller;

import com.hhplus.assignment.ecommerce.common.genericResponse.DataResponse;
import com.hhplus.assignment.ecommerce.common.genericResponse.GenericResponse;
import com.hhplus.assignment.ecommerce.order.controller.request.OrderRequestDto;
import com.hhplus.assignment.ecommerce.order.controller.response.OrderListResponse;
import com.hhplus.assignment.ecommerce.order.controller.response.OrderPaymentResponseDto;
import com.hhplus.assignment.ecommerce.order.controller.response.OrderResponseDto;
import com.hhplus.assignment.ecommerce.order.facade.OrderFacade;
import com.hhplus.assignment.ecommerce.order.service.OrderService;
import com.hhplus.assignment.ecommerce.product.controller.response.ProductDetailResponseDto;
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

    private final OrderService orderService;
    private final OrderFacade orderFacade;

    @Operation(summary = "주문 내역 조회", description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공",
                            content = @Content(schema = @Schema(implementation = OrderListResponse.class),
                                    examples = @ExampleObject(name = "정상응답", value = """
						 {
                             "data": [
                                 {
                                     "orderId": 1,
                                     "productOptionId": 1,
                                     "memberId": 1,
                                     "productName": "product1",
                                     "productOptionName": "option1",
                                     "orderStatus": "completed",
                                     "orderQuantity": 1,
                                     "orderPrice": 1000,
                                     "orderAt": "2024-07-05 00:24:19"
                                 },
                                 {
                                     "orderId": 1,
                                     "productOptionId": 1,
                                     "memberId": 1,
                                     "productName": "product1",
                                     "productOptionName": "option1",
                                     "orderStatus": "completed",
                                     "orderQuantity": 2,
                                     "orderPrice": 1000,
                                     "orderAt": "2024-07-05 00:24:19"
                                 },
                                 {
                                     "orderId": 1,
                                     "productOptionId": 1,
                                     "memberId": 1,
                                     "productName": "product1",
                                     "productOptionName": "option1",
                                     "orderStatus": "failed",
                                     "orderQuantity": 3,
                                     "orderPrice": 1000,
                                     "orderAt": "2024-07-05 00:24:19"
                                 }
                             ]
                         }
					""")))
            })
    // 주문 목록 조회
    @GetMapping("/{memberId}")
    public GenericResponse getOrderList(@PathVariable("memberId") Long memberId) {
        return OrderListResponse.create(orderService.getOrderList(memberId));
    }

    @Operation(summary = "주문 상세 조회", description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공",
                            content = @Content(schema = @Schema(implementation = OrderResponseDto.class),
                                    examples = @ExampleObject(name = "정상응답", value = """
						 {
                             "data": {
                                 "orderId": 1,
                                 "productOptionId": 1,
                                 "memberId": 1,
                                 "productName": "product1",
                                 "productOptionName": "option1",
                                 "orderStatus": "completed",
                                 "orderQuantity": 1,
                                 "orderPrice": 1000,
                                 "orderAt": "2024-07-05 00:24:19"
                             }
                         }
					""")))
            })
    // 주문 상세 조회
    @GetMapping("/detail/{orderId}")
    public GenericResponse getOrderDetail(@PathVariable("orderId") Long orderId) {
        return DataResponse.create(orderService.getOrderDetail(orderId));
    }

    @Operation(summary = "주문/결제", description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공",
                            content = @Content(schema = @Schema(implementation = OrderPaymentResponseDto.class),
                                    examples = @ExampleObject(name = "정상응답", value = """
						 {
                             "data": {
                                 "orderId": 1,
                                 "productOptionId": 1,
                                 "memberId": 1,
                                 "productName": "product1",
                                 "productOptionName": "option1",
                                 "orderStatus": "completed",
                                 "orderQuantity": 1,
                                 "orderPrice": 1000,
                                 "orderAt": "2024-07-05 00:24:19"
                             }
                         }
					""")))
            })
    // 주문/결제
    @PostMapping
    public GenericResponse orderPayment(@RequestBody OrderRequestDto requestDto) {
        return DataResponse.create(orderFacade.paymentOrder(requestDto));
    }


}
