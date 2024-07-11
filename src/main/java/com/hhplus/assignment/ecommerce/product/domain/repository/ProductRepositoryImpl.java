package com.hhplus.assignment.ecommerce.product.domain.repository;

import com.hhplus.assignment.ecommerce.common.model.CommonErrorCode;
import com.hhplus.assignment.ecommerce.exception.EcommerceException;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductEntity;
import com.hhplus.assignment.ecommerce.product.domain.jpaRepository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public List<ProductEntity> getProductList() {
        return productJpaRepository.findAll();
    }

    @Override
    public ProductEntity getProductDetail(Long productId) {
        return productJpaRepository.findById(productId)
                .orElseThrow(() -> new EcommerceException(HttpStatus.NOT_FOUND, CommonErrorCode.NOT_FOUND.getCode(), CommonErrorCode.NOT_FOUND.getMessage()));
    }

//    @Override
//    public TopSalesProductResponseDto getTopSalesProductList() {
//        return null;
//    }
}
