package com.hhplus.assignment.ecommerce.product.facede;

import com.hhplus.assignment.ecommerce.product.controller.response.TopSalesProductResponseDto;

import java.util.List;

public interface ProductFacade {

    List<TopSalesProductResponseDto> getTopSalesProductList();
}
