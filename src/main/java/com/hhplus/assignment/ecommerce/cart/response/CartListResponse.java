package com.hhplus.assignment.ecommerce.cart.response;

import com.hhplus.assignment.ecommerce.cart.response.dto.CartResponseDto;
import com.hhplus.assignment.ecommerce.common.genericResponse.DataListResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
public class CartListResponse extends DataListResponse<CartResponseDto> {

    public static CartListResponse create(List<CartResponseDto> cartResponseDtos) {
        return CartListResponse.builder()
                .data(cartResponseDtos)
                .build();
    }
}
