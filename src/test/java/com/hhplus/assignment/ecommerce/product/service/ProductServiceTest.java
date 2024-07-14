package com.hhplus.assignment.ecommerce.product.service;

import com.hhplus.assignment.ecommerce.product.controller.response.ProductDetailResponseDto;
import com.hhplus.assignment.ecommerce.product.controller.response.ProductResponseDto;
import com.hhplus.assignment.ecommerce.product.controller.response.TopSalesProductResponseDto;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductEntity;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductOptionEntity;
import com.hhplus.assignment.ecommerce.product.domain.repository.ProductOptionRepository;
import com.hhplus.assignment.ecommerce.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductOptionRepository productOptionRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private List<ProductEntity> productEntityList;
    private List<ProductOptionEntity> productOptionEntityList;

    @BeforeEach
    void setUp() {
        productEntityList = List.of(
                new ProductEntity(1L, "product", new BigDecimal(12000000)),
                new ProductEntity(2L, "product2", new BigDecimal(5500)),
                new ProductEntity(3L, "product3", new BigDecimal(10000)),
                new ProductEntity(4L, "product4", new BigDecimal(12000))
        );

        productOptionEntityList = List.of(
                new ProductOptionEntity(1L, 1L, "product_option1", new BigDecimal(12000000), 0),
                new ProductOptionEntity(1L, 1L, "product_option2", new BigDecimal(12000000), 0),
                new ProductOptionEntity(1L, 1L, "product_option3", new BigDecimal(12000000), 0),
                new ProductOptionEntity(1L, 1L, "product_option4", new BigDecimal(12000000), 0),
                new ProductOptionEntity(1L, 1L, "product_option5", new BigDecimal(12000000), 0)
        );
    }

    @Test
    @DisplayName("상품 목록 조회 테스트")
    void getProductList() {
        // given
        long productId = 1L;
        when(productRepository.getProductList()).thenReturn(productEntityList);
        when(productOptionRepository.isSoldOut(productId))
                .thenReturn(productOptionEntityList.stream().mapToInt(ProductOptionEntity::getStock).sum() == 0);

        // when
        List<ProductResponseDto> response = productService.getProductList();

        // then
        System.out.printf("response: %s%n", response);
        System.out.printf("response.get(0).id(): %s%n", response.get(0).id());
        System.out.printf("response.get(0).soldOut(): %s%n", response.get(0).soldOut());

        assertEquals(response.get(0).id(), productRepository.getProductList().get(0).getId());
        assertEquals(response.get(0).soldOut(), productOptionRepository.isSoldOut(productId));
    }

    @Test
    @DisplayName("상품 상세 조회 테스트")
    void getProductDetail() {
        // given
        long productId = 1L;
        when(productRepository.getProductDetail(productId)).thenReturn(productEntityList.get(0));
        when(productOptionRepository.getProductOptionList(productId)).thenReturn(productOptionEntityList);

        // when
        ProductDetailResponseDto response = productService.getProductDetail(productId);

        // then
        System.out.printf("response: %s%n", response);
        System.out.printf("response.getId(): %s%n", response.id());
        System.out.printf("response.getName(): %s%n", response.name());
        System.out.printf("response.getPrice(): %s%n", response.price());
        System.out.printf("response.getOptions(): %s%n", response.options());

        assertEquals(response.id(), productId);
        assertEquals(response.name(), "product");
        assertEquals(response.price(), new BigDecimal(12000000));
        assertEquals(response.options().get(0).id(), 1L);
    }

//    @Test
//    @DisplayName("상위상품 목록 조회 테스트")
//    void getTopSalesProductList() {
//        // given
//        when(productRepository.getTopSalesProductList()).thenReturn(productEntityList);
//
//        // when
//        TopSalesProductResponseDto response = productRepository.getTopSalesProductList();
//
//        // then
//        System.out.printf("response: %s%n", response);
//        System.out.printf("response.getTopSalesProductList(): %s%n", response.getTopSalesProductList());
//
//        assertEquals(response.getTopSalesProductList(), productRepository.getTopSalesProductList());
//    }
}
