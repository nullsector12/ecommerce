package com.hhplus.assignment.ecommerce.order.domain.exception;

import com.hhplus.assignment.ecommerce.exception.model.ErrorCode;

public enum OrderErrorCode implements ErrorCode {
    ORDER_NOT_FOUND("ORDER_001", "주문을 찾을 수 없습니다."),
    ORDER_ITEM_NOT_FOUND("ORDER_002", "주문 상품을 찾을 수 없습니다."),
    ORDER_ITEM_QUANTITY_INVALID("ORDER_003", "주문 상품 수량이 유효하지 않습니다."),
    ORDER_ITEM_SOLD_OUT("ORDER_004", "주문 상품이 품절되었습니다."),
    ORDER_ITEM_STOCK_NOT_ENOUGH("ORDER_005", "주문 상품의 재고가 부족합니다."),
    NOT_ENOUGH_MEMBER_BALANCE("ORDER_006", "회원 잔액이 부족합니다."),;

    private final String code;
    private final String message;

    OrderErrorCode(String code, String message) {
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
