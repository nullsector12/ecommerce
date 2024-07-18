package com.hhplus.assignment.ecommerce.wallet.domain.exception;

import com.hhplus.assignment.ecommerce.exception.model.ErrorCode;

public enum WalletErrorCode implements ErrorCode {
    NOT_FOUND_MEMBERS_WALLET("WALLET000", "회원의 지갑을 찾을 수 없습니다."),
    NOT_FOUND_WALLET_ID("WALLET001", "지갑을 찾을 수 없습니다."),
    NOT_ENOUGH_BALANCE("WALLET002", "잔액이 부족합니다."),
    ;

    private final String code;
    private final String message;

    WalletErrorCode(String code, String message) {
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
