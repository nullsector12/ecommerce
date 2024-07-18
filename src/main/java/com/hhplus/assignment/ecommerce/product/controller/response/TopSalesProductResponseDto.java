package com.hhplus.assignment.ecommerce.product.controller.response;


import com.hhplus.assignment.ecommerce.product.service.command.ProductCommand;

public record TopSalesProductResponseDto(
        Long productId,
        String productName,
        Integer totalOrderCount,
        Integer soldQuantity,
        Integer rank
) {
    public TopSalesProductResponseDto(ProductCommand.TopSalesProductInfo topSalesProductInfo, String productName) {
        this(topSalesProductInfo.id(), productName,
                topSalesProductInfo.totalOrderCount().intValue(), topSalesProductInfo.totalQuantity().intValue(),
                topSalesProductInfo.rank());
    }
}
