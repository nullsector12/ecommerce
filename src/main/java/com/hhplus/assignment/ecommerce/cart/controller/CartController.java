package com.hhplus.assignment.ecommerce.cart.controller;

import com.hhplus.assignment.ecommerce.cart.request.dto.CartRequestDto;
import com.hhplus.assignment.ecommerce.cart.response.CartListResponse;
import com.hhplus.assignment.ecommerce.common.genericResponse.DataResponse;
import com.hhplus.assignment.ecommerce.common.genericResponse.GenericResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

//    private final FakeCardResponse fakeCardResponse;
//
//    @GetMapping("/{memberId}")
//    public GenericResponse getCartList(@PathVariable("memberId") Long memberId) {
//        return CartListResponse.create(fakeCardResponse.cartResponseDtoList);
//    }
//
//    @PostMapping
//    public GenericResponse addProductToCart(@RequestBody CartRequestDto requestDto) {
//        log.info("카트에 상품 추가/제거 요청 : {}", requestDto);
//        return DataResponse.create("카트 업데이트 성공");
//    }
}
