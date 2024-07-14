package com.hhplus.assignment.ecommerce.product.controller;

import com.hhplus.assignment.ecommerce.common.genericResponse.DataResponse;
import com.hhplus.assignment.ecommerce.common.genericResponse.GenericResponse;
import com.hhplus.assignment.ecommerce.product.controller.response.ProductDetailResponseDto;
import com.hhplus.assignment.ecommerce.product.controller.response.ProductListResponse;
import com.hhplus.assignment.ecommerce.product.facede.ProductFacade;
import com.hhplus.assignment.ecommerce.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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

    private final ProductService productService;
    private final ProductFacade productFacade;

    @Operation(summary = "상품 목록 조회", description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공",
                            content = @Content(schema = @Schema(implementation = ProductListResponse.class),
                                    examples = @ExampleObject(name = "정상응답", value = """
						{
                             "data": [
                                 {
                                     "id": 1,
                                     "name": "product1",
                                     "price": 1000,
                                     "soldOut": false
                                 },
                                 {
                                     "id": 2,
                                     "name": "product2",
                                     "price": 2000,
                                     "soldOut": false
                                 },
                                 {
                                     "id": 3,
                                     "name": "product3",
                                     "price": 3000,
                                     "soldOut": false
                                 }
                             ]
                        }
					""")))
            })
    // 상품 목록 조회
    @GetMapping
    public GenericResponse getProductList() {

    return ProductListResponse.create(productService.getProductList());
    }

    @Operation(summary = "상품 상세 조회", description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공",
                            content = @Content(schema = @Schema(implementation = ProductDetailResponseDto.class),
                                    examples = @ExampleObject(name = "정상응답", value = """
						 {
                             "data": {
                                 "id": 1,
                                 "name": "product1",
                                 "price": 1000,
                                 "options": [
                                     {
                                         "id": 1,
                                         "productId": 1,
                                         "option": "option1",
                                         "optionPrice": 100,
                                         "stock": 10
                                     },
                                     {
                                         "id": 2,
                                         "productId": 1,
                                         "option": "option2",
                                         "optionPrice": 100,
                                         "stock": 20
                                     },
                                     {
                                         "id": 3,
                                         "productId": 1,
                                         "option": "option3",
                                         "optionPrice": 150,
                                         "stock": 30
                                     }
                                 ]
                             }
                         }
					""")))
            })
    // 상품 상세 조회
    @GetMapping("/{productId}")
    public GenericResponse getProductDetail(@PathVariable("productId") Long productId) {

        return DataResponse.create(productService.getProductDetail(productId));
    }

    @Operation(summary = "상위 상품 조회", description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공",
                            content = @Content(schema = @Schema(implementation = ProductListResponse.class),
                                    examples = @ExampleObject(name = "정상응답", value = """
                         {
                             "data": [
                                 {
                                     "productId": 1,
                                     "productName": "product1",
                                     "totalOrderCount": 10000,
                                     "soldQuantity": 10000,
                                     "rank": 1
                                 },
                                 {
                                     "productId": 2,
                                     "productName": "product2",
                                     "totalOrderCount": 1000,
                                     "soldQuantity": 9000,
                                     "rank": 2
                                 },
                                 {
                                     "productId": 3,
                                     "productName": "product3",
                                     "totalOrderCount": 100,
                                     "soldQuantity": 8000,
                                     "rank": 3
                                 },
                                 {
                                     "productId": 4,
                                     "productName": "product4",
                                     "totalOrderCount": 10,
                                     "soldQuantity": 7000,
                                     "rank": 4
                                 },
                                 {
                                     "productId": 5,
                                     "productName": "product5",
                                     "totalOrderCount": 1,
                                     "soldQuantity": 6000,
                                     "rank": 5
                                 }
                             ]
                         }
					""")))
            })
    @GetMapping("/top-sales")
    public GenericResponse getTopSalesProductList() {

        return DataResponse.create(productFacade.getTopSalesProductList());
    }
}
