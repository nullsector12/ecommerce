package com.hhplus.assignment.ecommerce.product.domain.repository;

import com.hhplus.assignment.ecommerce.product.domain.entity.ProductOptionEntity;

import java.util.List;

public interface ProductOptionRepository {

    boolean isSoldOut(Long productId);

    ProductOptionEntity getProductOption(Long productOptionId);

    List<ProductOptionEntity> getProductOptionList(Long productId);

    List<ProductOptionEntity> getProductOptionList(List<Long> productOptionIds);

    List<ProductOptionEntity> getProductOptionListForUpdate(List<Long> productOptionIds);

    void decreaseProductOptionStock(Long productOptionId, int quantity);

    List<ProductOptionEntity> createProductOption(List<ProductOptionEntity> productOptionEntity);
}
