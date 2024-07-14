package com.hhplus.assignment.ecommerce.order.domain.JpaRepository;

import com.hhplus.assignment.ecommerce.order.domain.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByMemberId(long memberId);

    @Query("SELECT o FROM OrderEntity o " +
            "JOIN ProductOptionEntity po on o.productOptionId = po.id " +
            "WHERE o.status = :status " +
            "AND o.orderAt >= :orderAt " +
            "GROUP BY o.productOptionId " +
            "ORDER BY COUNT(o) DESC " +
            "LIMIT 5")
    List<OrderEntity> findAllByStatusAndOrderAt(@Param("status") String status, @Param("orderAt") LocalDateTime orderAt);

    List<OrderEntity> findAllByIdAndStatus(Long id, String ordered);
}
