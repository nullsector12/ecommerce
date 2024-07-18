package com.hhplus.assignment.ecommerce.product.domain.repository;

import com.hhplus.assignment.ecommerce.exception.EcommerceException;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductEntity;
import com.hhplus.assignment.ecommerce.product.domain.exception.ProductErrorCode;
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
                .orElseThrow(() -> EcommerceException.create(HttpStatus.NOT_FOUND, ProductErrorCode.PRODUCT_NOT_FOUND));
    }

    @Override
    public String getProductName(Long productId) {
        return productJpaRepository.findById(productId)
                .map(ProductEntity::getName)
                .orElseThrow(() -> EcommerceException.create(HttpStatus.NOT_FOUND, ProductErrorCode.PRODUCT_NOT_FOUND));
    }

    @Override
    public List<ProductEntity> createProduct(List<ProductEntity> productEntity) {
        return productJpaRepository.saveAll(productEntity);
    }
}
