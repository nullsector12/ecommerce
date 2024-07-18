package com.hhplus.assignment.ecommerce.order.facade;

import com.hhplus.assignment.ecommerce.order.controller.request.OrderRequestDto;
import com.hhplus.assignment.ecommerce.order.controller.response.OrderPaymentResponseDto;
import com.hhplus.assignment.ecommerce.order.domain.entity.OrderEntity;
import com.hhplus.assignment.ecommerce.order.service.OrderService;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductEntity;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductOptionEntity;
import com.hhplus.assignment.ecommerce.product.service.ProductService;
import com.hhplus.assignment.ecommerce.product.service.dto.ProductOptionDetailDto;
import com.hhplus.assignment.ecommerce.product.service.dto.ProductOptionPaymentDto;
import com.hhplus.assignment.ecommerce.wallet.controller.response.WalletResponseDto;
import com.hhplus.assignment.ecommerce.wallet.service.WalletService;
import com.hhplus.assignment.ecommerce.wallet.service.dto.WalletDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderFacadeTest {

    @Mock
    private OrderService orderService;
    @Mock
    private ProductService productService;
    @Mock
    private WalletService walletService;

    @InjectMocks
    private OrderFacadeImpl orderFacade;

    @Test
    @DisplayName("주문/결제")
    void orderPayment() {
        // given
        long orderId = 1L;
        long productId = 10L;
        long memberId = 1L;
        long productOptionId = 12L;
        int quantity = 2;
        BigDecimal orderPrice = new BigDecimal(8000);

        OrderEntity orderEntity = new OrderEntity(orderId, productOptionId, memberId, "ordered", quantity,
                new BigDecimal(8000), LocalDateTime.now());
        ProductOptionDetailDto productOptionDetailDto = new ProductOptionDetailDto(
                ProductEntity.builder()
                        .id(productId)
                        .name("product1")
                        .price(new BigDecimal(4500))
                        .build(),
                ProductOptionEntity.builder()
                        .id(productOptionId)
                        .productId(productId)
                        .optionPrice(new BigDecimal(4000))
                        .option("option1")
                        .stock(8)
                        .build());

        // 회원의 잔액차감 후 반환
        WalletResponseDto walletResponseDto = new WalletResponseDto(new WalletDto(1L, memberId, new BigDecimal(2000)));
        when(walletService.useBalance(1L, orderPrice)).thenReturn(walletResponseDto);

        // 상품 재고 업데이트 후 반환
        ProductOptionPaymentDto productOptionPaymentDto =
                new ProductOptionPaymentDto(new ProductOptionEntity(productOptionId, productId, "option1",
                        new BigDecimal(4000), 10));
        OrderRequestDto orderRequestDto =
                new OrderRequestDto(memberId, productOptionId, quantity, orderPrice);
        when(productService.decreaseProductOptionStock(
                orderRequestDto.productOptionId(), orderRequestDto.quantity())).thenReturn(productOptionPaymentDto);
        // 저장된 주문 정보를 반환
        OrderPaymentResponseDto orderPaymentResponseDto = new OrderPaymentResponseDto(orderEntity, productOptionDetailDto);
        when(orderService.saveOrder(orderRequestDto)).thenReturn(orderPaymentResponseDto);

        // when
        OrderPaymentResponseDto result = orderFacade.paymentOrder(orderRequestDto);

        // then
        assertEquals(orderPaymentResponseDto, result);
        assertEquals(orderId, result.orderId());
        assertEquals(memberId, result.memberId());
        assertEquals(productOptionId, result.productOptionId());
        assertEquals(quantity, result.orderQuantity());
        assertEquals(orderPrice, result.orderPrice());
    }
}
