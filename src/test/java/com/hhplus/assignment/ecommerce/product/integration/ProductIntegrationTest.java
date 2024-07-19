package com.hhplus.assignment.ecommerce.product.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.assignment.ecommerce.order.domain.entity.OrderEntity;
import com.hhplus.assignment.ecommerce.order.domain.repository.OrderItemRepository;
import com.hhplus.assignment.ecommerce.order.domain.repository.OrderRepository;
import com.hhplus.assignment.ecommerce.order.service.command.OrderCommand;
import com.hhplus.assignment.ecommerce.product.controller.response.ProductDetailResponseDto;
import com.hhplus.assignment.ecommerce.product.controller.response.ProductResponseDto;
import com.hhplus.assignment.ecommerce.product.controller.response.TopSalesProductResponseDto;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductEntity;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductOptionEntity;
import com.hhplus.assignment.ecommerce.product.domain.repository.ProductOptionRepository;
import com.hhplus.assignment.ecommerce.product.domain.repository.ProductRepository;
import com.hhplus.assignment.ecommerce.product.facede.ProductFacade;
import com.hhplus.assignment.ecommerce.product.service.ProductService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductFacade productFacade;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @BeforeEach
    void setUp() {
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
    @DisplayName("상품 목록 조회")
    void getProductList() throws Exception {
        // when
        List<ProductResponseDto> productList = productFacade.getProductList();

        // then
        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(productList.size())))
                .andExpect(jsonPath("$.data[0].id").value(productList.get(0).id()))
                .andExpect(jsonPath("$.data[0].name").value(productList.get(0).name()))
                .andExpect(jsonPath("$.data[0].price").value(productList.get(0).price()))
                .andExpect(jsonPath("$.data[0].soldOut").value(productList.get(0).soldOut()))
        ;
    }

    @Test
    @DisplayName("상품 상세 조회")
    void getProductDetail() throws Exception {
        // given
        Long productId = 1L;

        // when
        ProductDetailResponseDto productDetail = productFacade.getProductDetail(productId);

        // then
        mockMvc.perform(get("/product/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(productDetail.id()))
                .andExpect(jsonPath("$.data.name").value(productDetail.name()))
                .andExpect(jsonPath("$.data.price").value(productDetail.price()))
                .andExpect(jsonPath("$.data.options", hasSize(productDetail.options().size())))
                .andExpect(jsonPath("$.data.options[0].stock").value(productDetail.options().get(0).stock()))
                .andExpect(jsonPath("$.data.options[1].stock").value(productDetail.options().get(1).stock()))
        ;
    }

    @Test
    @DisplayName("상위 상품 조회")
    void getTopSalesProductList() throws Exception {
        // when
        List<TopSalesProductResponseDto> topSalesProductList = productFacade.getTopSalesProductList();

        // then
        mockMvc.perform(get("/product/top-sales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(topSalesProductList.size())))
                .andExpect(jsonPath("$.data[0].rank").value(topSalesProductList.get(0).rank()))
                .andExpect(jsonPath("$.data[0].productId").value(topSalesProductList.get(0).productId()))
                .andExpect(jsonPath("$.data[0].productName").value(topSalesProductList.get(0).productName()))
                .andExpect(jsonPath("$.data[0].soldQuantity").value(topSalesProductList.get(0).soldQuantity()))
        ;
    }
}
