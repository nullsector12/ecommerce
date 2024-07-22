package com.hhplus.assignment.ecommerce.product.domain.entity;

import com.hhplus.assignment.ecommerce.exception.EcommerceException;
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
@Table(name = "product_option")
public class ProductOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    private String option;

    @Column(name = "option_price")
    private BigDecimal optionPrice;

    private Integer stock;

    public void decreaseStock(int quantity) {
        this.stock -= quantity;
    }
}
