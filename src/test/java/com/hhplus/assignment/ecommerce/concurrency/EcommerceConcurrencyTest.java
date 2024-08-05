package com.hhplus.assignment.ecommerce.concurrency;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.assignment.ecommerce.order.controller.request.OrderRequestDto;
import com.hhplus.assignment.ecommerce.order.controller.response.OrderPaymentResponseDto;
import com.hhplus.assignment.ecommerce.order.domain.repository.OrderItemRepository;
import com.hhplus.assignment.ecommerce.order.domain.repository.OrderRepository;
import com.hhplus.assignment.ecommerce.order.facade.OrderFacade;
import com.hhplus.assignment.ecommerce.product.controller.response.ProductDetailResponseDto;
import com.hhplus.assignment.ecommerce.product.domain.repository.ProductOptionRepository;
import com.hhplus.assignment.ecommerce.product.domain.repository.ProductRepository;
import com.hhplus.assignment.ecommerce.product.facede.ProductFacade;
import com.hhplus.assignment.ecommerce.wallet.domain.repository.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EcommerceConcurrencyTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderFacade orderFacade;

    @Autowired
    private ProductFacade productFacade;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    @DisplayName("주문/결제 - 주문과 동시에 상품조회")
    void paymentOrder_concurrency_test() throws InterruptedException {
        // given
        final int threadCount = 2;
        final ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        final CountDownLatch latch = new CountDownLatch(threadCount);
        Long orderMemberId = 1L;
        Long orderProductId = 1L;
        BigDecimal orderPrice = new BigDecimal(2000);
        // ProductOptionEntity.builder().productId(1L).option("상품1 옵션1").optionPrice(new BigDecimal(1000)).stock(10).build(),
        // id = 1의 상품옵션을 1개 주문
        List<OrderRequestDto.OrderItemRequestDto> orderItemRequestDtos = List.of(
                OrderRequestDto.OrderItemRequestDto.builder().productOptionId(1L).quantity(1).build()
        );
        OrderRequestDto requestDto = OrderRequestDto.builder()
                .memberId(orderMemberId)
                .orderItemRequestDtos(orderItemRequestDtos)
                .orderPrice(orderPrice)
                .build();


        // when
        AtomicReference<OrderPaymentResponseDto> responseDto = new AtomicReference<>();
        AtomicReference<ProductDetailResponseDto> productDetailResponseDto = new AtomicReference<>();
        for (int i = 0; i < threadCount; i++) {
            if(i == 0) {
                executorService.execute(() -> {
                    try {
                        // 회원 A의 상품 주문/결제 요청 (재고 10개 중 1개 주문)
                        responseDto.set(orderFacade.paymentOrder(requestDto));
                    } finally {
                        latch.countDown();
                    }
                });
            } else {
                executorService.execute(() -> {
                    try {
                        productDetailResponseDto.set(productFacade.getProductDetail(orderProductId));;
                    } finally {
                        latch.countDown();
                    }
                });
            }
        }
        latch.await();
        Thread.sleep(500);

        // then
        assertThat(productDetailResponseDto.get().options().get(0).stock()).isEqualTo(9);
    }

    @Test
    @DisplayName("주문/결제 - 동일한 상품을 동시에 주문할 때, 재고가 부족해지는 경우")
    void paymentOrder_concurrency_test_not_enough_stock() throws InterruptedException {
        // given
        final int threadCount = 2;
        final ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        final CountDownLatch latch = new CountDownLatch(threadCount);
        Long orderMemberAId = 1L;
        Long orderMemberBId = 2L;
        Long orderProductId = 1L;
        BigDecimal orderPrice = new BigDecimal(6000);
        // ProductOptionEntity.builder().productId(1L).option("상품1 옵션1").optionPrice(new BigDecimal(1000)).stock(10).build(),
        // A회원: id = 1의 상품옵션을 6개 주문
        List<OrderRequestDto.OrderItemRequestDto> orderMemberA = List.of(
                OrderRequestDto.OrderItemRequestDto.builder().productOptionId(1L).quantity(6).build()
        );
        // B회원: id = 1의 상품옵션을 6개 주문
        List<OrderRequestDto.OrderItemRequestDto> orderMemberB = List.of(
                OrderRequestDto.OrderItemRequestDto.builder().productOptionId(1L).quantity(6).build()
        );
        OrderRequestDto orderA = OrderRequestDto.builder()
                .memberId(orderMemberAId)
                .orderItemRequestDtos(orderMemberA)
                .orderPrice(orderPrice)
                .build();

        OrderRequestDto orderB = OrderRequestDto.builder()
                .memberId(orderMemberBId)
                .orderItemRequestDtos(orderMemberB)
                .orderPrice(orderPrice)
                .build();

        // when
        AtomicReference<OrderPaymentResponseDto> orderPaymentMemberA = new AtomicReference<>();
        AtomicReference<OrderPaymentResponseDto> orderPaymentMemberB = new AtomicReference<>();
        for (int i = 0; i < threadCount; i++) {
            if(i == 0) {
                executorService.execute(() -> {
                    try {
                        // 회원 A의 상품 주문/결제 요청 (재고 10개 중 6개 주문)
                        orderPaymentMemberA.set(orderFacade.paymentOrder(orderA));
                    } finally {
                        latch.countDown();
                    }
                });
            } else {
                executorService.execute(() -> {
                    try {
                        // 회원 B의 상품 주문/결제 요청 (재고 10개 중 6개 주문)
                        orderPaymentMemberB.set(orderFacade.paymentOrder(orderB));
                    } finally {
                        latch.countDown();
                    }
                });
            }
        }
        latch.await();

        // then
        Assertions.assertAll(
                () -> assertThat(orderPaymentMemberA.get().getOrderItems().get(0).productId()).isEqualTo(orderProductId),
                () -> assertThat(orderPaymentMemberB.get().getOrderItems().get(0).productId()).isEqualTo(orderProductId)
        );
    }

    @Test
    @DisplayName("상위 상품 조회")
    void getTopSalesProductList() throws InterruptedException {
        // given

        // when
        long startTime = System.currentTimeMillis();
        IntStream.range(0, 100).forEach(i -> {
            productFacade.getTopSalesProductList();
        });

        // then
        long endTime = System.currentTimeMillis();
        System.out.println("실행 시간: " + (endTime - startTime) + "ms");
    }
}
