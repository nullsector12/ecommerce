package com.hhplus.assignment.ecommerce.exception.model;

import org.springframework.http.HttpStatus;

/**
 * 신규 에러코드 정의 시 implements
 *
 */
public interface ErrorCode {

    String getCode();
    String getMessage();
}
