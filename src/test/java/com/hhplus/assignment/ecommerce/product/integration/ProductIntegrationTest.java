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
