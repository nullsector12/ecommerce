package com.hhplus.assignment.ecommerce.order.controller.response;

import com.hhplus.assignment.ecommerce.common.genericResponse.DataListResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
public class OrderListResponse extends DataListResponse<OrderResponseDto> {

    public static OrderListResponse create(List<OrderResponseDto> orderResponseDtos) {
        return OrderListResponse.builder()
                .data(orderResponseDtos)
                .build();
    }
}
