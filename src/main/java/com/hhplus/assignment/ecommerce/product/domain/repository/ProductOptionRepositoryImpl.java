package com.hhplus.assignment.ecommerce.product.domain.repository;

import com.hhplus.assignment.ecommerce.exception.EcommerceException;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductOptionEntity;
import com.hhplus.assignment.ecommerce.product.domain.exception.ProductErrorCode;
import com.hhplus.assignment.ecommerce.product.domain.jpaRepository.ProductOptionJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductOptionRepositoryImpl implements ProductOptionRepository {

    private final ProductOptionJpaRepository productOptionJpaRepository;

    @Override
    public boolean isSoldOut(Long productId) {
        List<ProductOptionEntity> productOptionEntities = productOptionJpaRepository.findAllByProductId(productId);
        return productOptionEntities.stream().mapToInt(ProductOptionEntity::getStock).sum() == 0;
    }

    @Override
    public ProductOptionEntity getProductOption(Long productOptionId) {
        return productOptionJpaRepository.findById(productOptionId)
                .orElseThrow(() -> EcommerceException.create(HttpStatus.NOT_FOUND, ProductErrorCode.PRODUCT_OPTION_NOT_FOUND));
    }

    @Override
    public List<ProductOptionEntity> getProductOptionList(Long productId) {
        return productOptionJpaRepository.findAllByProductId(productId);
    }

    @Override
    public List<ProductOptionEntity> getProductOptionList(List<Long> productOptionIds) {
        return productOptionJpaRepository.findAllByIdIn(productOptionIds);
    }

    @Override
    public void decreaseProductOptionStock(Long productOptionId, int quantity) {
        ProductOptionEntity productOption = productOptionJpaRepository.findById(productOptionId)
                .orElseThrow(() -> EcommerceException.create(HttpStatus.NOT_FOUND, ProductErrorCode.PRODUCT_OPTION_NOT_FOUND));

        productOption.decreaseStock(quantity);
    }
}
