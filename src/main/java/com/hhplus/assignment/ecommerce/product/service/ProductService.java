package com.hhplus.assignment.ecommerce.product.service;

import com.hhplus.assignment.ecommerce.product.domain.entity.ProductEntity;
import com.hhplus.assignment.ecommerce.product.domain.repository.ProductOptionRepository;
import com.hhplus.assignment.ecommerce.product.domain.repository.ProductRepository;
import com.hhplus.assignment.ecommerce.product.service.command.ProductCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    public List<ProductCommand.ProductInfo> getProductList() {
        List<ProductEntity> productList = productRepository.getProductList();
        return productList.stream().map(product -> new ProductCommand.ProductInfo(product, productOptionRepository.isSoldOut(product.getId()))).toList();
    }

    public ProductCommand.ProductDetailInfo getProductDetail(Long productId) {
        ProductEntity product = productRepository.getProductDetail(productId);

        return new ProductCommand.ProductDetailInfo(product, productOptionRepository.getProductOptionList(productId));
    }

    @Transactional
    public List<ProductCommand.ProductDetailInfo.ProductOptionInfo> getProductOptionList(List<Long> productOptionIds) {
        return productOptionRepository.getProductOptionList(productOptionIds).stream().map(ProductCommand.ProductDetailInfo.ProductOptionInfo::new).toList();
    }

    @Transactional
    public List<ProductCommand.ProductDetailInfo.ProductOptionInfo> getProductOptionListForUpdate(List<Long> productOptionIds) {
        return productOptionRepository.getProductOptionListForUpdate(productOptionIds).stream().map(ProductCommand.ProductDetailInfo.ProductOptionInfo::new).toList();
    }

    @Transactional
    public void decreaseProductOptionStock(Long productOptionId, Integer quantity) {
        productOptionRepository.decreaseProductOptionStock(productOptionId, quantity);
    }

    public String getProductName(Long productId) {
        return productRepository.getProductName(productId);
    }
}
