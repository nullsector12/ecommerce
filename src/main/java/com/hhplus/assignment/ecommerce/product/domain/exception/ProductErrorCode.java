package com.hhplus.assignment.ecommerce.product.domain.exception;

import com.hhplus.assignment.ecommerce.exception.model.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ProductErrorCode implements ErrorCode {
    PRODUCT_NOT_FOUND("product_not_found", "상품을 찾을 수 없습니다."),
    PRODUCT_OPTION_NOT_FOUND("product_option_not_found", "상품 옵션을 찾을 수 없습니다."),
    PRODUCT_OPTION_STOCK_NOT_ENOUGH("product_option_stock_not_enough", "상품 재고가 부족합니다."),
    ;

    private final String code;
    private final String message;

    ProductErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
