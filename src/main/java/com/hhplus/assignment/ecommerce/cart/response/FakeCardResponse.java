package com.hhplus.assignment.ecommerce.cart.response;

import com.hhplus.assignment.ecommerce.cart.domain.entity.CartEntity;
import com.hhplus.assignment.ecommerce.cart.response.dto.CartResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class FakeCardResponse {

    public List<CartResponseDto> cartResponseDtoList = getCartResponseDtoList();

    private List<CartResponseDto> getCartResponseDtoList() {

        List<CartEntity> cartEntities = List.of(
            new CartEntity(1L, 1L, 1L, 1, "wait", LocalDateTime.now(), LocalDateTime.now()),
            new CartEntity(1L, 2L, 2L, 2, "wait", LocalDateTime.now(), LocalDateTime.now()),
            new CartEntity(1L, 3L, 3L, 3, "wait", LocalDateTime.now(), LocalDateTime.now())
        );

        List<CartResponseDto> cartResponseDtos = new ArrayList<>();

        for(int i = 0; i < cartEntities.size(); i++) {
            cartResponseDtos.add(new CartResponseDto(cartEntities.get(i), "product" + i, "option" + i,  1000));
        }

        return cartResponseDtos;
    }
}
