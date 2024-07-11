package com.hhplus.assignment.ecommerce.product.facade;

import com.hhplus.assignment.ecommerce.order.service.OrderService;
import com.hhplus.assignment.ecommerce.order.service.dto.OrderTopRateDto;
import com.hhplus.assignment.ecommerce.product.controller.response.TopSalesProductResponseDto;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductEntity;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductOptionEntity;
import com.hhplus.assignment.ecommerce.product.domain.repository.ProductRepository;
import com.hhplus.assignment.ecommerce.product.facede.ProductFacadeImpl;
import com.hhplus.assignment.ecommerce.product.service.ProductService;
import com.hhplus.assignment.ecommerce.product.service.dto.ProductOptionDetailDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductFacadeTest {

    @Mock
    private OrderService orderService;
    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductFacadeImpl productFacade;

    @Test
    @DisplayName("상품 판매량 순위 조회")
    void getTopSalesProductList() {
        // given
        List<OrderTopRateDto> topRateOrder = List.of(
                new OrderTopRateDto(1L, 1L, 200, 100000, 100000, 1)
        );
        when(orderService.getTopRateOrder()).thenReturn(topRateOrder);
        ProductOptionDetailDto productOptionDetailDto = new ProductOptionDetailDto(
                ProductEntity.builder()
                        .id(1L)
                        .name("product1")
                        .build(),
                ProductOptionEntity.builder()
                .id(1L)
                .productId(1L)
                .option("option1")
                .stock(10)
                .build()
        );
        when(productService.getProductOptionDetail(1L)).thenReturn(productOptionDetailDto);

        TopSalesProductResponseDto topSalesProductResponseDto = new TopSalesProductResponseDto(
                productOptionDetailDto, topRateOrder.get(0)
        );

        // when
        List<TopSalesProductResponseDto> topSalesProductResponseDtoList = productFacade.getTopSalesProductList();

        // then
        assertEquals(topSalesProductResponseDto, topSalesProductResponseDtoList.get(0));
    }
}
