package com.hhplus.assignment.ecommerce.product.controller.response;

import com.hhplus.assignment.ecommerce.common.genericResponse.DataListResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
public class ProductListResponse extends DataListResponse<ProductResponseDto> {

    public static ProductListResponse create(List<ProductResponseDto> productResponseDtos) {
        return ProductListResponse.builder()
            .data(productResponseDtos)
            .build();
    }
}
