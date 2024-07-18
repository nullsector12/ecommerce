package com.hhplus.assignment.ecommerce.order.facade;

import com.hhplus.assignment.ecommerce.exception.EcommerceException;
import com.hhplus.assignment.ecommerce.order.controller.request.OrderRequestDto;
import com.hhplus.assignment.ecommerce.order.controller.response.OrderPaymentResponseDto;
import com.hhplus.assignment.ecommerce.order.controller.response.OrderResponseDto;
import com.hhplus.assignment.ecommerce.order.domain.exception.OrderErrorCode;
import com.hhplus.assignment.ecommerce.order.service.OrderService;
import com.hhplus.assignment.ecommerce.order.service.command.OrderCommand;
import com.hhplus.assignment.ecommerce.product.service.ProductService;
import com.hhplus.assignment.ecommerce.product.service.command.ProductCommand;
import com.hhplus.assignment.ecommerce.wallet.controller.response.WalletResponseDto;
import com.hhplus.assignment.ecommerce.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;
    private final ProductService productService;
    private final WalletService walletService;

    public List<OrderResponseDto> getOrderList(Long memberId) {

        return orderService.getOrderList(memberId).stream().map(OrderResponseDto::new).toList();
    }

    public OrderResponseDto getOrderDetail(Long orderId) {
        return new OrderResponseDto(orderService.getOrderDetail(orderId));
    }

    public OrderPaymentResponseDto paymentOrder(OrderRequestDto dto) throws EcommerceException {

        OrderPaymentResponseDto response = null;

        try {
            // 1. 회원 잔액 가져오기
            WalletResponseDto wallet = walletService.getWallet(dto.memberId());

            // 2. 잔액과 총 주문금액 비교
            if(wallet.balance().compareTo(dto.orderPrice()) < 0) {
                log.error("잔액 부족. memberId: {}, balance: {}, orderPrice: {}", dto.memberId(), wallet.balance(), dto.orderPrice());
                throw EcommerceException.create(HttpStatus.BAD_REQUEST, OrderErrorCode.NOT_ENOUGH_MEMBER_BALANCE);
            }

            // 3. 상품 옵션 리스트 가져오기
            List<ProductCommand.ProductDetailInfo.ProductOptionInfo> productOptionInfos =
                    productService.getProductOptionList(dto.orderItemRequestDtos()
                            .stream().map(OrderRequestDto.OrderItemRequestDto::productOptionId).toList());

            // 4. 주문량과 재고 비교 및 재고 차감
            for(OrderRequestDto.OrderItemRequestDto item : dto.orderItemRequestDtos()) {
                ProductCommand.ProductDetailInfo.ProductOptionInfo productOptionInfo = productOptionInfos.stream()
                        .filter(info -> info.id().equals(item.productOptionId()))
                        .findFirst()
                        .orElseThrow(() -> EcommerceException.create(HttpStatus.NOT_FOUND, OrderErrorCode.ORDER_ITEM_NOT_FOUND));

                if(productOptionInfo.stock() < item.quantity()) {
                    log.error("재고 부족. productOptionId: {}, stock: {}, quantity: {}", item.productOptionId(), productOptionInfo.stock(), item.quantity());
                    throw EcommerceException.create(HttpStatus.BAD_REQUEST, OrderErrorCode.ORDER_ITEM_STOCK_NOT_ENOUGH);
                }
                productService.decreaseProductOptionStock(item.productOptionId(), item.quantity());
            }

            // 5. 회원 잔액 차감
            walletService.useBalance(wallet.walletId(), dto.orderPrice());

            // 6. 주문/주문항목 생성
            response = new OrderPaymentResponseDto(orderService.saveOrder(OrderCommand.CreateOrder.builder()
                    .memberId(dto.memberId())
                    .totalPrice(dto.orderPrice())
                    .orderItems(productOptionInfos.stream().map(e ->
                            OrderCommand.CreateOrder.CreateOrderItem.builder()
                                    .productId(e.productId())
                                    .productOptionId(e.id())
                                    .productName(productService.getProductName(e.productId()))
                                    .quantity(dto.orderItemRequestDtos().stream()
                                            .filter(i -> i.productOptionId().equals(e.id()))
                                            .findFirst()
                                            .map(OrderRequestDto.OrderItemRequestDto::quantity)
                                            .orElseThrow(() -> EcommerceException.create(HttpStatus.NOT_FOUND, OrderErrorCode.ORDER_ITEM_NOT_FOUND))
                                    )
                                    .productOptionName(e.optionName())
                                    .productOptionPrice(e.optionPrice())
                                    .build()).toList()
                    )
                    .build()));

            // todo: 7. 외부 데이터플랫폼에 생성된 주문정보 전송
            // sendOrderResponse(response);

            // 8. 주문항목/주문 데이터 리턴
            return response;

        } catch (Exception e) {
            log.error("주문 실패: {}", e.getMessage());
            // todo: 주문 실패시 rollback 처리?(잔액 복구, 재고 복구)
            // fixme: rollback 처리는 transactional로 처리되잖아
        }
        return response;
    }
}
