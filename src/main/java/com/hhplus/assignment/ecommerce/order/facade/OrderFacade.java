package com.hhplus.assignment.ecommerce.order.facade;

import com.hhplus.assignment.ecommerce.order.controller.request.OrderRequestDto;
import com.hhplus.assignment.ecommerce.order.controller.response.OrderPaymentResponseDto;

public interface OrderFacade {

    OrderPaymentResponseDto paymentOrder(OrderRequestDto dto);

}
