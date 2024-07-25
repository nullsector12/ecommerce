package com.hhplus.assignment.ecommerce.order.facade;

import com.hhplus.assignment.ecommerce.exception.EcommerceException;
import com.hhplus.assignment.ecommerce.order.controller.request.OrderRequestDto;
import com.hhplus.assignment.ecommerce.order.controller.response.OrderPaymentResponseDto;
import com.hhplus.assignment.ecommerce.order.domain.exception.OrderErrorCode;
import com.hhplus.assignment.ecommerce.order.service.OrderService;
import com.hhplus.assignment.ecommerce.product.service.ProductService;
import com.hhplus.assignment.ecommerce.product.service.command.ProductCommand;
import com.hhplus.assignment.ecommerce.wallet.controller.response.WalletResponseDto;
import com.hhplus.assignment.ecommerce.wallet.service.WalletService;
import com.hhplus.assignment.ecommerce.wallet.service.dto.WalletDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    private OrderFacade orderFacade;

    @Test
    @DisplayName("주문/결제 - 잔액이 부족해 결제 실패하는 경우")
    void orderPayment_not_enough_member_balance() {
        // given
        long memberId = 1L;
        long productOptionId = 12L;
        int quantity = 2;
        BigDecimal remainBalance = new BigDecimal(2000);
        BigDecimal orderPrice = new BigDecimal(8000);

        List<OrderRequestDto.OrderItemRequestDto> orderItemRequestDtoList =
                List.of(
                        new OrderRequestDto.OrderItemRequestDto(productOptionId, quantity)
                );

        OrderRequestDto orderRequestDto = new OrderRequestDto(memberId, orderItemRequestDtoList, orderPrice);

        WalletResponseDto walletResponseDto = new WalletResponseDto(new WalletDto(1L, memberId, remainBalance));
        when(walletService.getWallet(1L)).thenReturn(walletResponseDto);

        // when
        OrderPaymentResponseDto result = orderFacade.paymentOrder(orderRequestDto);

        // then
        // 회원의 잔액과 요청받은 주문금액 확인 후 에러 발생
        assertThatExceptionOfType(EcommerceException.class).isThrownBy(() -> {
            throw EcommerceException.create(HttpStatus.BAD_REQUEST, OrderErrorCode.NOT_ENOUGH_MEMBER_BALANCE);
            });
    }

    @Test
    @DisplayName("주문/결제 - 재고가 부족해 결제 실패하는 경우")
    void orderPayment_not_enough_product_option_stock() {
        // given
        long memberId = 1L;
        long productOptionId = 12L;
        int remainStock = 2;
        int orderQuantity = 5;

        List<OrderRequestDto.OrderItemRequestDto> orderItemRequestDtoList =
                List.of(
                        new OrderRequestDto.OrderItemRequestDto(productOptionId, orderQuantity)
                );

        OrderRequestDto orderRequestDto = new OrderRequestDto(
                memberId, orderItemRequestDtoList, new BigDecimal(8000)
        );
        WalletResponseDto walletResponseDto = new WalletResponseDto(
                new WalletDto(1L, memberId, new BigDecimal(8000))
        );
        when(walletService.getWallet(1L)).thenReturn(walletResponseDto);

        List<ProductCommand.ProductDetailInfo.ProductOptionInfo> productOptionInfos = List.of(
                new ProductCommand.ProductDetailInfo.ProductOptionInfo(
                        productOptionId, 1L, "option", new BigDecimal(1000), remainStock
                )
        );

        when(productService.getProductOptionListForUpdate(List.of(productOptionId))).thenReturn(productOptionInfos);

        // when
        OrderPaymentResponseDto result = orderFacade.paymentOrder(orderRequestDto);

        // then
        // 상품옵션의 재고와 요청받은 주문량 확인 후 에러 발생
        assertThatExceptionOfType(EcommerceException.class).isThrownBy(() -> {
            throw EcommerceException.create(HttpStatus.BAD_REQUEST, OrderErrorCode.ORDER_ITEM_STOCK_NOT_ENOUGH);
        });
    }
}
