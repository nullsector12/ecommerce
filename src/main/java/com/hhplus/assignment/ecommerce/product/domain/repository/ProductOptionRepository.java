package com.hhplus.assignment.ecommerce.product.domain.repository;

import com.hhplus.assignment.ecommerce.product.domain.entity.ProductOptionEntity;

import java.util.List;

public interface ProductOptionRepository {

    boolean isSoldOut(Long productId);

    ProductOptionEntity getProductOption(Long productOptionId);

    List<ProductOptionEntity> getProductOptionList(Long productId);

    ProductOptionEntity paymentProductOption(Long productOptionId, int quantity);
}
