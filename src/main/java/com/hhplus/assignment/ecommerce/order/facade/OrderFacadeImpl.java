package com.hhplus.assignment.ecommerce.order.facade;

import com.hhplus.assignment.ecommerce.order.controller.request.OrderRequestDto;
import com.hhplus.assignment.ecommerce.order.controller.response.OrderPaymentResponseDto;
import com.hhplus.assignment.ecommerce.order.service.OrderService;
import com.hhplus.assignment.ecommerce.product.service.ProductService;
import com.hhplus.assignment.ecommerce.product.service.dto.ProductOptionPaymentDto;
import com.hhplus.assignment.ecommerce.wallet.controller.response.WalletResponseDto;
import com.hhplus.assignment.ecommerce.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderFacadeImpl implements OrderFacade{

    private final OrderService orderService;
    private final ProductService productService;
    private final WalletService walletService;

    @Override
    public OrderPaymentResponseDto paymentOrder(OrderRequestDto dto) {

        ProductOptionPaymentDto productOptionPaymentDto = productService.decreaseProductOptionStock(dto.productOptionId(), dto.quantity());
        WalletResponseDto wallet = walletService.useBalance(dto.memberId(), dto.orderPrice());

        // todo: 외부 데이터플랫폼에 주문정보 전송

        return orderService.saveOrder(dto);
    }
}
