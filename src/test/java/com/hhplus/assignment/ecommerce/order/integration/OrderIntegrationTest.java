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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
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

    @BeforeEach
    void setUp() {
        walletRepository.createWallet(1L);
        walletRepository.chargeBalance(new ChargeBalanceDto(1L, new BigDecimal(3000)));

        productRepository.createProduct(List.of(
                ProductEntity.builder().name("상품1").price(new BigDecimal(1000)).build(),
                ProductEntity.builder().name("상품2").price(new BigDecimal(1000)).build(),
                ProductEntity.builder().name("상품3").price(new BigDecimal(1000)).build(),
                ProductEntity.builder().name("상품4").price(new BigDecimal(1000)).build(),
                ProductEntity.builder().name("상품5").price(new BigDecimal(1000)).build()
        ));

        productOptionRepository.createProductOption(List.of(
                ProductOptionEntity.builder().productId(1L).option("상품1 옵션1").optionPrice(new BigDecimal(1000)).stock(10).build(),
                ProductOptionEntity.builder().productId(1L).option("상품1 옵션2").optionPrice(new BigDecimal(1000)).stock(10).build(),
                ProductOptionEntity.builder().productId(2L).option("상품2 옵션1").optionPrice(new BigDecimal(1000)).stock(10).build(),
                ProductOptionEntity.builder().productId(2L).option("상품2 옵션2").optionPrice(new BigDecimal(1000)).stock(10).build(),
                ProductOptionEntity.builder().productId(3L).option("상품3 옵션1").optionPrice(new BigDecimal(1000)).stock(10).build(),
                ProductOptionEntity.builder().productId(3L).option("상품3 옵션2").optionPrice(new BigDecimal(1000)).stock(10).build(),
                ProductOptionEntity.builder().productId(3L).option("상품3 옵션3").optionPrice(new BigDecimal(1000)).stock(10).build(),
                ProductOptionEntity.builder().productId(4L).option("상품4 옵션1").optionPrice(new BigDecimal(1000)).stock(0).build(),
                ProductOptionEntity.builder().productId(4L).option("상품4 옵션2").optionPrice(new BigDecimal(1000)).stock(0).build(),
                ProductOptionEntity.builder().productId(5L).option("상품5 옵션1").optionPrice(new BigDecimal(1000)).stock(10).build(),
                ProductOptionEntity.builder().productId(5L).option("상품5 옵션2").optionPrice(new BigDecimal(1000)).stock(10).build(),
                ProductOptionEntity.builder().productId(5L).option("상품5 옵션3").optionPrice(new BigDecimal(1000)).stock(10).build(),
                ProductOptionEntity.builder().productId(5L).option("상품5 옵션4").optionPrice(new BigDecimal(1000)).stock(10).build()
        ));

        OrderEntity orderEntity1 = orderRepository.saveOrder(OrderCommand.CreateOrder.builder().memberId(1L).totalPrice(new BigDecimal(100000)).build());
        OrderEntity orderEntity2 = orderRepository.saveOrder(OrderCommand.CreateOrder.builder().memberId(2L).totalPrice(new BigDecimal(5000)).build());

        orderItemRepository.saveOrderItem(orderEntity1.getId(), OrderCommand.CreateOrder.CreateOrderItem.builder().productId(1L).productOptionId(1L).productName("상품1").productOptionName("상품1 옵션1").productOptionPrice(new BigDecimal(1000)).quantity(1).build());
        orderItemRepository.saveOrderItem(orderEntity1.getId(), OrderCommand.CreateOrder.CreateOrderItem.builder().productId(2L).productOptionId(3L).productName("상품2").productOptionName("상품2 옵션1").productOptionPrice(new BigDecimal(1000)).quantity(1).build());
        orderItemRepository.saveOrderItem(orderEntity1.getId(), OrderCommand.CreateOrder.CreateOrderItem.builder().productId(2L).productOptionId(4L).productName("상품2").productOptionName("상품2 옵션2").productOptionPrice(new BigDecimal(1000)).quantity(2).build());
        orderItemRepository.saveOrderItem(orderEntity1.getId(), OrderCommand.CreateOrder.CreateOrderItem.builder().productId(5L).productOptionId(13L).productName("상품5").productOptionName("상품5 옵션4").productOptionPrice(new BigDecimal(1000)).quantity(5).build());
        orderItemRepository.saveOrderItem(orderEntity2.getId(), OrderCommand.CreateOrder.CreateOrderItem.builder().productId(3L).productOptionId(5L).productName("상품3").productOptionName("상품3 옵션1").productOptionPrice(new BigDecimal(1000)).quantity(2).build());
        orderItemRepository.saveOrderItem(orderEntity2.getId(), OrderCommand.CreateOrder.CreateOrderItem.builder().productId(1L).productOptionId(1L).productName("상품1").productOptionName("상품1 옵션1").productOptionPrice(new BigDecimal(1000)).quantity(12).build());
        orderItemRepository.saveOrderItem(orderEntity2.getId(), OrderCommand.CreateOrder.CreateOrderItem.builder().productId(4L).productOptionId(9L).productName("상품4").productOptionName("상품4 옵션2").productOptionPrice(new BigDecimal(1000)).quantity(20).build());
    }

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
