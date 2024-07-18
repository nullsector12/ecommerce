package com.hhplus.assignment.ecommerce.order.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Builder
@Table(name = "order_item")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_option_id")
    private Long productOptionId;

    private Integer quantity;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_option_name")
    private String productOptionName;

    @Column(name = "product_option_price")
    private BigDecimal productOptionPrice;
}
