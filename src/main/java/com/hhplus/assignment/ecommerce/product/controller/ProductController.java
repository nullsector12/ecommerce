package com.hhplus.assignment.ecommerce.product.controller;

import com.hhplus.assignment.ecommerce.common.genericResponse.DataResponse;
import com.hhplus.assignment.ecommerce.common.genericResponse.GenericResponse;
import com.hhplus.assignment.ecommerce.product.controller.response.ProductDetailResponseDto;
import com.hhplus.assignment.ecommerce.product.controller.response.ProductListResponse;
import com.hhplus.assignment.ecommerce.product.controller.response.TopSalesProductListResponse;
import com.hhplus.assignment.ecommerce.product.facede.ProductFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "상품", description = "상품 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductFacade productFacade;

    @Operation(summary = "상품 목록 조회", description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공",
                            content = @Content(schema = @Schema(implementation = ProductListResponse.class)))
            })
    // 상품 목록 조회
    @GetMapping
    public GenericResponse getProductList() {
        return ProductListResponse.create(productFacade.getProductList());
    }

    @Operation(summary = "상품 상세 조회", description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공",
                            content = @Content(schema = @Schema(implementation = ProductDetailResponseDto.class)))
            })
    // 상품 상세 조회
    @GetMapping("/{productId}")
    public GenericResponse getProductDetail(@PathVariable("productId") Long productId) {
        return DataResponse.create(productFacade.getProductDetail(productId));
    }

    @Operation(summary = "상위 상품 조회", description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공",
                            content = @Content(schema = @Schema(implementation = TopSalesProductListResponse.class)))
            })
    @GetMapping("/top-sales")
    public GenericResponse getTopSalesProductList() {
        return TopSalesProductListResponse.create(productFacade.getTopSalesProductList());
    }
}
