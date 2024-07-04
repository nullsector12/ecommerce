package com.hhplus.assignment.ecommerce.product.response;

import com.hhplus.assignment.ecommerce.product.domain.entity.ProductEntity;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductOptionEntity;
import com.hhplus.assignment.ecommerce.product.response.dto.ProductDetailResponseDto;
import com.hhplus.assignment.ecommerce.product.response.dto.ProductResponseDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class FakeProductResponse {

    public List<ProductResponseDto> productResponseDtoList = getProductResponseDtoList();
    public ProductDetailResponseDto productDetailResponseDto = getProductDetailResponseDto();


    private List<ProductResponseDto> getProductResponseDtoList() {

        List<ProductEntity> productEntityList = List.of(
                new ProductEntity(1L, "product1", BigDecimal.valueOf(1000)),
                new ProductEntity(2L, "product2", BigDecimal.valueOf(2000)),
                new ProductEntity(3L, "product3", BigDecimal.valueOf(3000))
        );

        return productEntityList.stream()
                .map(entity -> new ProductResponseDto(entity, false))
                .toList();
    }

    private ProductDetailResponseDto getProductDetailResponseDto() {

        ProductEntity productEntity = new ProductEntity(1L, "product1", BigDecimal.valueOf(1000));

        List<ProductOptionEntity> productOptionEntities = List.of(
                new ProductOptionEntity(1L, 1L, "option1", BigDecimal.valueOf(100), 10),
                new ProductOptionEntity(2L, 1L, "option2", BigDecimal.valueOf(100), 20),
                new ProductOptionEntity(3L, 1L, "option3", BigDecimal.valueOf(150), 30)
        );

        return new ProductDetailResponseDto(productEntity, productOptionEntities);
    }
}
