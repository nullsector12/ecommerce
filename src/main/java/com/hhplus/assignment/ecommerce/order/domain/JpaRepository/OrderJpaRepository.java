package com.hhplus.assignment.ecommerce.order.domain.JpaRepository;

import com.hhplus.assignment.ecommerce.order.domain.entity.OrderEntity;
import com.hhplus.assignment.ecommerce.order.service.dto.TopOrderedProductInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByMemberId(long memberId);

    @Query(value = """
            select oi.product_id, sum(oi.quantity) as totalQuantity, count(oi.product_id) as totalOrderCount, sum(oi.quantity * oi.product_option_price) as totalOrderAmount 
            from orders o join order_item oi 
                on o.id = oi.order_id  
            where now() >= order_at and order_at >= date_add(now(), Interval -3 day)
            group by oi.product_id 
            order by sum(oi.quantity) desc 
            limit 5""", nativeQuery = true)
    List<TopOrderedProductInterface> findTopOrderProduct();
}
