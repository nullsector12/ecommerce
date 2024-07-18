package com.hhplus.assignment.ecommerce.product.facede;

import com.hhplus.assignment.ecommerce.order.service.OrderService;
import com.hhplus.assignment.ecommerce.order.service.dto.OrderTopRateDto;
import com.hhplus.assignment.ecommerce.product.controller.response.ProductDetailResponseDto;
import com.hhplus.assignment.ecommerce.product.controller.response.ProductResponseDto;
import com.hhplus.assignment.ecommerce.product.controller.response.TopSalesProductResponseDto;
import com.hhplus.assignment.ecommerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductFacade {

    private final ProductService productService;
    private final OrderService orderService;

    public List<ProductResponseDto> getProductList() {
        return productService.getProductList().stream().map(ProductResponseDto::new).toList();
    }

    public ProductDetailResponseDto getProductDetail(Long productId) {
        return new ProductDetailResponseDto(productService.getProductDetail(productId));
    }

//    public List<TopSalesProductResponseDto> getTopSalesProductList() {
//        List<OrderTopRateDto> topRateOrder = orderService.getTopRateOrder();
//        List<TopSalesProductResponseDto> topSalesProductResponseDtoList = new ArrayList<>();
//        for(OrderTopRateDto topRateDto : topRateOrder) {
//            topSalesProductResponseDtoList.add(
//                    new TopSalesProductResponseDto(
//                            productService.getProductOptionDetail(topRateDto.productOptionId()),
//                            topRateDto
//                    )
//            );
//        }
//        return topSalesProductResponseDtoList;
//    }
}
