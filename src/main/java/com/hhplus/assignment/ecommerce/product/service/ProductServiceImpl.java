package com.hhplus.assignment.ecommerce.product.service;

import com.hhplus.assignment.ecommerce.product.controller.response.ProductDetailResponseDto;
import com.hhplus.assignment.ecommerce.product.controller.response.ProductOptionResponseDto;
import com.hhplus.assignment.ecommerce.product.controller.response.ProductResponseDto;
import com.hhplus.assignment.ecommerce.product.controller.response.TopSalesProductResponseDto;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductEntity;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductOptionEntity;
import com.hhplus.assignment.ecommerce.product.domain.repository.ProductOptionRepository;
import com.hhplus.assignment.ecommerce.product.domain.repository.ProductRepository;
import com.hhplus.assignment.ecommerce.product.service.dto.ProductOptionDetailDto;
import com.hhplus.assignment.ecommerce.product.service.dto.ProductOptionPaymentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    @Override
    public List<ProductResponseDto> getProductList() {
        List<ProductEntity> productList = productRepository.getProductList();
        return productList.stream().map(product -> new ProductResponseDto(product, productOptionRepository.isSoldOut(product.getId()))).toList();
    }

    @Override
    public ProductDetailResponseDto getProductDetail(Long productId) {
        ProductEntity product = productRepository.getProductDetail(productId);

        return new ProductDetailResponseDto(product, productOptionRepository.getProductOptionList(productId));
    }

    @Override
    public ProductOptionDetailDto getProductOptionDetail(Long productOptionId) {
        ProductOptionEntity productOption = productOptionRepository.getProductOption(productOptionId);
        ProductEntity product = productRepository.getProductDetail(productOption.getProductId());

        return new ProductOptionDetailDto(product, productOption);
    }

    @Override
    public ProductOptionPaymentDto updateProductOptionStockForPayment(Long productOptionId, Integer quantity) {
        ProductOptionEntity productOption = productOptionRepository.paymentProductOption(productOptionId, quantity);
        return new ProductOptionPaymentDto(productOption);
    }
}
