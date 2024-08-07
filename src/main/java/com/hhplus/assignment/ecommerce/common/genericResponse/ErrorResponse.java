package com.hhplus.assignment.ecommerce.common.genericResponse;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.hhplus.assignment.ecommerce.exception.model.ErrorCode;
import com.hhplus.assignment.ecommerce.exception.model.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse implements GenericResponse {

    @JsonUnwrapped
    private ErrorResult error;

    public static ErrorResponse create(ErrorResult error) {
        return new ErrorResponse(error);
    }

    public static ErrorResponse create(ErrorCode errorCode) {
        return new ErrorResponse(ErrorResult.builder()
            .code(errorCode.getCode())
            .message(errorCode.getMessage())
            .build());
    }

}
