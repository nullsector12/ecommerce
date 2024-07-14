package com.hhplus.assignment.ecommerce.product.domain.repository;

import com.hhplus.assignment.ecommerce.product.domain.entity.ProductEntity;

import java.util.List;

public interface ProductRepository {

    // 상품 목록 조회
    List<ProductEntity> getProductList();

    // 상품 상세 조회
    ProductEntity getProductDetail(Long productId);

    // 인기 상품 목록 조회
//    ProductEntity getTopSalesProductList();
}
