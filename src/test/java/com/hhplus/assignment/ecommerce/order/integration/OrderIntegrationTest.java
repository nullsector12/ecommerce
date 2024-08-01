package com.hhplus.assignment.ecommerce.order.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.assignment.ecommerce.order.controller.request.OrderRequestDto;
import com.hhplus.assignment.ecommerce.order.controller.response.OrderPaymentResponseDto;
import com.hhplus.assignment.ecommerce.order.controller.response.OrderResponseDto;
import com.hhplus.assignment.ecommerce.order.domain.entity.OrderEntity;
import com.hhplus.assignment.ecommerce.order.domain.repository.OrderItemRepository;
import com.hhplus.assignment.ecommerce.order.domain.repository.OrderRepository;
import com.hhplus.assignment.ecommerce.order.facade.OrderFacade;
import com.hhplus.assignment.ecommerce.order.service.command.OrderCommand;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductEntity;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductOptionEntity;
import com.hhplus.assignment.ecommerce.product.domain.repository.ProductOptionRepository;
import com.hhplus.assignment.ecommerce.product.domain.repository.ProductRepository;
import com.hhplus.assignment.ecommerce.product.facede.ProductFacade;
import com.hhplus.assignment.ecommerce.wallet.domain.repository.WalletRepository;
import com.hhplus.assignment.ecommerce.wallet.service.dto.ChargeBalanceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OrderIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderFacade orderFacade;

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
    @DisplayName("주문 목록 조회")
    void getOrderList() throws Exception {
        // given
        Long memberId = 1L;

        // when
        List<OrderResponseDto> orderResponseDtos = orderFacade.getOrderList(memberId);

        // then
        mockMvc.perform(get("/order/{memberId}", memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(orderResponseDtos.size())))
                .andExpect(jsonPath("$.data[0].memberId").value(orderResponseDtos.get(0).getMemberId()))
                .andExpect(jsonPath("$.data[0].totalPrice").value(orderResponseDtos.get(0).getTotalPrice()))
                .andExpect(jsonPath("$.data[0].orderItems", hasSize(orderResponseDtos.get(0).getOrderItems().size())))
                .andExpect(jsonPath("$.data[0].orderItems[0].productId").value(orderResponseDtos.get(0).getOrderItems().get(0).getProductId()))
        ;
    }

    @Test
    @DisplayName("주문 상세 조회")
    void getOrderDetail() throws Exception {
        // given
        Long orderId = 1L;

        // when
        OrderResponseDto orderResponseDto = orderFacade.getOrderDetail(orderId);

        // then
        mockMvc.perform(get("/order/detail/{orderId}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(orderResponseDto.getMemberId()))
                .andExpect(jsonPath("$.data.totalPrice").value(orderResponseDto.getTotalPrice()))
                .andExpect(jsonPath("$.data.orderItems", hasSize(orderResponseDto.getOrderItems().size())))
                .andExpect(jsonPath("$.data.orderItems[0].productId").value(orderResponseDto.getOrderItems().get(0).getProductId()))
        ;
    }

    @Test
    @DisplayName("주문/결제 - 잔액 부족으로 실패")
    void paymentOrder_NOT_ENOUGH_BALANCE() throws Exception {
        // given
        Long memberId = 1L;
        BigDecimal orderPrice = new BigDecimal(4000);
        List<OrderRequestDto.OrderItemRequestDto> orderItemRequestDtos = List.of(
                OrderRequestDto.OrderItemRequestDto.builder().productOptionId(1L).quantity(2).build(),
                OrderRequestDto.OrderItemRequestDto.builder().productOptionId(5L).quantity(2).build()
        );
        OrderRequestDto requestDto = OrderRequestDto.builder()
                .memberId(memberId)
                .orderItemRequestDtos(orderItemRequestDtos)
                .orderPrice(orderPrice)
                .build();

        // when
        String content = objectMapper.writeValueAsString(requestDto);
        OrderPaymentResponseDto orderResponseDto = orderFacade.paymentOrder(requestDto);

        // then
        mockMvc.perform(post("/order")
                        .content(content)
                        .contentType("application/json"))
                .andExpect(status().is4xxClientError())
        ;
    }

    @Test
    @DisplayName("주문/결제")
    void paymentOrder() throws Exception {
        // given
        Long memberId = 1L;
        BigDecimal orderPrice = new BigDecimal(2000);
        List<OrderRequestDto.OrderItemRequestDto> orderItemRequestDtos = List.of(
                OrderRequestDto.OrderItemRequestDto.builder().productOptionId(1L).quantity(1).build(),
                OrderRequestDto.OrderItemRequestDto.builder().productOptionId(5L).quantity(1).build()
        );
        OrderRequestDto requestDto = OrderRequestDto.builder()
                .memberId(memberId)
                .orderItemRequestDtos(orderItemRequestDtos)
                .orderPrice(orderPrice)
                .build();


        // when
        String content = objectMapper.writeValueAsString(requestDto);
        OrderPaymentResponseDto orderResponseDto = orderFacade.paymentOrder(requestDto);

        // then
        mockMvc.perform(post("/order")
                .content(content)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(orderResponseDto.getMemberId()))
                .andExpect(jsonPath("$.data.totalPrice").value(orderResponseDto.getTotalPrice()))
                .andExpect(jsonPath("$.data.orderItems", hasSize(orderResponseDto.getOrderItems().size())))
                .andExpect(jsonPath("$.data.orderItems[0].productId").value(orderResponseDto.getOrderItems().get(0).productId()))
        ;
    }
}
