package com.hhplus.assignment.ecommerce.product.domain.repository;

import com.hhplus.assignment.ecommerce.product.domain.entity.ProductOptionEntity;
import com.hhplus.assignment.ecommerce.product.domain.jpaRepository.ProductOptionJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
                .orElseThrow(() -> new IllegalArgumentException("상품 옵션 정보가 없습니다."));
    }

    @Override
    public List<ProductOptionEntity> getProductOptionList(Long productId) {
        return productOptionJpaRepository.findAllByProductId(productId);
    }

    @Override
    public ProductOptionEntity paymentProductOption(Long productOptionId, int quantity) {
        ProductOptionEntity productOption = productOptionJpaRepository.findById(productOptionId)
                .orElseThrow(() -> new IllegalArgumentException("상품 옵션 정보가 없습니다."));

        return productOption.payment(quantity);
    }
}
