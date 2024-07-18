package com.hhplus.assignment.ecommerce.product.facede;

import com.hhplus.assignment.ecommerce.order.service.OrderService;
import com.hhplus.assignment.ecommerce.order.service.command.OrderCommand;
import com.hhplus.assignment.ecommerce.order.service.dto.TopOrderedProduct;
import com.hhplus.assignment.ecommerce.product.controller.response.ProductDetailResponseDto;
import com.hhplus.assignment.ecommerce.product.controller.response.ProductResponseDto;
import com.hhplus.assignment.ecommerce.product.controller.response.TopSalesProductResponseDto;
import com.hhplus.assignment.ecommerce.product.service.ProductService;
import com.hhplus.assignment.ecommerce.product.service.command.ProductCommand;
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

    public List<TopSalesProductResponseDto> getTopSalesProductList() {
        List<ProductCommand.TopSalesProductInfo> topSalesProductList = new ArrayList<>();
        List<OrderCommand.TopOrderedProduct> topRateOrder = orderService.getTopRateOrder();

        int rank = 1;
        for(OrderCommand.TopOrderedProduct topRateDto : topRateOrder) {
            topSalesProductList.add(
                    new ProductCommand.TopSalesProductInfo(
                            productService.getProductDetail(topRateDto.productId()),
                            topRateDto,
                            rank++
                    )
            );
        }
        return topSalesProductList.stream().map(product ->
                new TopSalesProductResponseDto(product, productService.getProductName(product.id()))).toList();
    }
}
