package com.hhplus.assignment.ecommerce.exception;

import com.hhplus.assignment.ecommerce.common.genericResponse.GenericException;
import com.hhplus.assignment.ecommerce.exception.model.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = false)
public class EcommerceException extends GenericException {

    private final HttpStatus httpStatus;
    private final String code;

    public EcommerceException(HttpStatus httpStatus, String code, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.code = code;
    }

    public static EcommerceException create(HttpStatus httpStatus, ErrorCode errorCode) {
        return new EcommerceException(httpStatus, errorCode.getCode(), errorCode.getMessage());
    }

}
