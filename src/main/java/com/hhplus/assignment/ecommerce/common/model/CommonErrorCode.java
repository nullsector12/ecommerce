package com.hhplus.assignment.ecommerce.common.model;

import com.hhplus.assignment.ecommerce.exception.model.ErrorCode;

public enum CommonErrorCode implements ErrorCode {
    INVALID_REQUEST("invalid_request", "잘못된 요청입니다."),
    UNKNOWN_ERROR("unknown_error", "알 수 없는 에러가 발생했습니다."),
    UNAUTHORIZED("unauthorized", "인증되지 않은 사용자입니다."),
    FORBIDDEN("forbidden", "접근 권한이 없습니다."),
    NOT_FOUND("not_found", "요청하신 정보를 찾을 수 없습니다."),
    INTERNAL_ERROR("internal_error", "서버 내부 오류입니다.");

    private final String code;
    private final String message;

    CommonErrorCode(String code, String message) {
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
