package com.hhplus.assignment.ecommerce.order.domain.JpaRepository;

import com.hhplus.assignment.ecommerce.order.domain.entity.OrderEntity;
import com.hhplus.assignment.ecommerce.order.service.command.OrderCommand;
import com.hhplus.assignment.ecommerce.order.service.dto.TopOrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByMemberId(long memberId);

    @Query(value = """
            select oi.productId, sum(oi.quantity) as totalQuantity, count(oi.productId) as totalOrderCount, 
            sum(oi.quantity*oi.productOptionPrice) as totalOrderAmount 
            from OrderEntity o join OrderItemEntity oi 
            on o.id = oi.orderId 
            where o.orderAt >= :orderAt 
            group by oi.productId 
            order by sum(oi.quantity) desc 
            limit 5""", nativeQuery = true)
    List<OrderCommand.TopOrderedProduct> findTopOrderProduct(@Param("orderAt") LocalDateTime orderAt);
}
