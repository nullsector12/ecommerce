package com.hhplus.assignment.ecommerce.order.service.dto;

public interface TopOrderedProductInterface {

    Long getProductId();
    Long getTotalQuantity();
    Long getTotalOrderCount();
    Long getTotalOrderAmount();
}
