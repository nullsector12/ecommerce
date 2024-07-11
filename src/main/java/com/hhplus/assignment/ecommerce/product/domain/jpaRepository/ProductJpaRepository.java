package com.hhplus.assignment.ecommerce.product.domain.jpaRepository;

import com.hhplus.assignment.ecommerce.product.domain.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
}
