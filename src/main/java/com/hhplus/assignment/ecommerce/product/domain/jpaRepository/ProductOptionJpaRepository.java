package com.hhplus.assignment.ecommerce.product.domain.jpaRepository;

import com.hhplus.assignment.ecommerce.product.domain.entity.ProductOptionEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductOptionJpaRepository extends JpaRepository<ProductOptionEntity, Long> {
    List<ProductOptionEntity> findAllByProductId(Long id);

    List<ProductOptionEntity> findAllByIdIn(List<Long> productOptionIds);

    // Pessimistic write lock 처리 추가
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select po from ProductOptionEntity po where po.id in :productOptionIds")
    List<ProductOptionEntity> findAllByIdInForUpdate(@Param("productOptionIds") List<Long> productOptionIds);
}
