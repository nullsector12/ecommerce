package com.hhplus.assignment.ecommerce.product.service;

import com.hhplus.assignment.ecommerce.product.controller.response.ProductDetailResponseDto;
import com.hhplus.assignment.ecommerce.product.controller.response.ProductOptionResponseDto;
import com.hhplus.assignment.ecommerce.product.controller.response.ProductResponseDto;
import com.hhplus.assignment.ecommerce.product.controller.response.TopSalesProductResponseDto;
import com.hhplus.assignment.ecommerce.product.service.dto.ProductOptionDetailDto;
import com.hhplus.assignment.ecommerce.product.service.dto.ProductOptionPaymentDto;

import java.util.List;

public interface ProductService {

    // 상품 목록 조회
    List<ProductResponseDto> getProductList();

    // 상품 상세 조회
    ProductDetailResponseDto getProductDetail(Long productId);

    // 상품옵션 상세조회
    ProductOptionDetailDto getProductOptionDetail(Long productOptionId);

    // 상품 주문
    ProductOptionPaymentDto updateProductOptionStockForPayment(Long productOptionId, Integer quantity);


}
