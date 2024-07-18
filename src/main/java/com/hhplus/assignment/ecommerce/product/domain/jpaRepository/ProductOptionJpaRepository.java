package com.hhplus.assignment.ecommerce.product.domain.jpaRepository;

import com.hhplus.assignment.ecommerce.product.domain.entity.ProductOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOptionJpaRepository extends JpaRepository<ProductOptionEntity, Long> {
    List<ProductOptionEntity> findAllByProductId(Long id);

    List<ProductOptionEntity> findAllByIdIn(List<Long> productOptionIds);
}
