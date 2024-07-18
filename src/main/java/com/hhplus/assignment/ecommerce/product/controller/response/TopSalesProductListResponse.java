package com.hhplus.assignment.ecommerce.product.controller.response;

import com.hhplus.assignment.ecommerce.common.genericResponse.DataListResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
public class TopSalesProductListResponse extends DataListResponse<TopSalesProductResponseDto> {

    public static TopSalesProductListResponse create(List<TopSalesProductResponseDto> topSalesProductResponseDtos) {
        return TopSalesProductListResponse.builder()
            .data(topSalesProductResponseDtos)
            .build();
    }
}
