package com.hhplus.assignment.ecommerce.product.controller;

import com.hhplus.assignment.ecommerce.common.genericResponse.DataResponse;
import com.hhplus.assignment.ecommerce.common.genericResponse.GenericResponse;
import com.hhplus.assignment.ecommerce.product.response.FakeProductResponse;
import com.hhplus.assignment.ecommerce.product.response.ProductListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final FakeProductResponse fakeProductResponse;

    // 상품 목록 조회
    @GetMapping
    public GenericResponse getProductList() {

        return ProductListResponse.create(fakeProductResponse.productResponseDtoList);
    }

    // 상품 상세 조회
    @GetMapping("/{productId}")
    public GenericResponse getProductDetail(@PathVariable("productId") Long productId) {

        return DataResponse.create(fakeProductResponse.productDetailResponseDto);
    }
}
