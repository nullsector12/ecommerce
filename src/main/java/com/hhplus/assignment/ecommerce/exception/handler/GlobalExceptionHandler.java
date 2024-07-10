package com.hhplus.assignment.ecommerce.exception.handler;

import com.hhplus.assignment.ecommerce.common.genericResponse.ErrorResponse;
import com.hhplus.assignment.ecommerce.common.model.CommonErrorCode;
import com.hhplus.assignment.ecommerce.exception.model.ErrorResult;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ApiResponse(responseCode = "400", description = "잘못된 요청 정보",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(value = """
				{
					"code": "invalid_request",
					"message": "잘못된 요청입니다."
				}
				""")
            )
    )
    @ExceptionHandler({
            BindException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            ConstraintViolationException.class, // @Validated 검증 실패
            ServletRequestBindingException.class,
            MissingRequestHeaderException.class, // 필수 RequestHeader 누락
            NoHandlerFoundException.class, // 정의되지 않은 URL
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestException(Exception ex) {
        log.error("BadRequestException, {}", ex.getMessage(), ex);

        return ErrorResponse.create(ErrorResult.builder()
                .code(CommonErrorCode.INVALID_REQUEST.getCode())
                .message(CommonErrorCode.INVALID_REQUEST.getMessage())
                .build());
    }

    @ApiResponse(responseCode = "404", description = "지원하지 않는 리소스 (URL 등)",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = """
					{
						"code": "not_found",
						"message": "요청하신 정보를 찾을 수 없습니다."
					}
					""")
            )
    )
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundException(Exception e) {
        log.error("Exception, {}", e.getMessage(), e);

        return ErrorResponse.create(ErrorResult.builder()
                .code(CommonErrorCode.NOT_FOUND.getCode())
                .message(CommonErrorCode.NOT_FOUND.getMessage())
                .build());
    }

    /**
     * 미처리된 에러
     *
     * @param ex
     * @return
     */
    @ApiResponse(responseCode = "500", description = "서버 오류",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                    examples = @ExampleObject(
                            value = """
					{
						"code": "unknown_error",
						"message": "서버 오류입니다."
					}
					"""
                    )
            )
    )
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ErrorResponse unhandledException(Exception ex) {
        log.error("Unknown error occurred, {}, ", ex.getMessage(), ex);

        return ErrorResponse.create(ErrorResult.builder()
                .code(CommonErrorCode.UNKNOWN_ERROR.getCode())
                .message(CommonErrorCode.UNKNOWN_ERROR.getMessage())
                .build());
    }
}
