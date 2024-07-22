package com.hhplus.assignment.ecommerce.product.service.command;

import com.hhplus.assignment.ecommerce.order.service.command.OrderCommand;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductEntity;
import com.hhplus.assignment.ecommerce.product.domain.entity.ProductOptionEntity;

import java.math.BigDecimal;
import java.util.List;

public class ProductCommand {

    public record ProductInfo(
            Long id,
            String name,
            BigDecimal price,
            boolean isSoldOut
    ) {
        public ProductInfo(ProductEntity product, boolean isSoldOut){
            this(product.getId(), product.getName(), product.getPrice(), isSoldOut);
        }
    }

    public record ProductDetailInfo(
            Long id,
            String name,
            BigDecimal price,
            List<ProductOptionInfo> options
    ) {

        public ProductDetailInfo(ProductEntity product, List<ProductOptionEntity> productOptions){
            this(product.getId(), product.getName(), product.getPrice(), productOptions.stream()
                    .map(ProductOptionInfo::new)
                    .toList());
        }

        public record ProductOptionInfo(
                Long id,
                Long productId,
                String optionName,
                BigDecimal optionPrice,
                Integer stock
        ) {
            public ProductOptionInfo(ProductOptionEntity productOption){
                this(productOption.getId(), productOption.getProductId(), productOption.getOption(), productOption.getOptionPrice()
                        , productOption.getStock());
            }
        }
    }

    public record TopSalesProductInfo(
            Long id,
            Long totalOrderCount,
            Long totalQuantity,
            Long totalOrderAmount,
            Integer rank
    ) {
        public TopSalesProductInfo(ProductDetailInfo productInfo,
                                   OrderCommand.TopOrderedProduct topRateProduct,
                                   Integer rank)
            {
            this(productInfo.id(), topRateProduct.totalOrderCount(),
                    topRateProduct.totalQuantity(), topRateProduct.totalOrderAmount(), rank);
        }
    }
}
