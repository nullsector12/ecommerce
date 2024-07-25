package com.hhplus.assignment.ecommerce.wallet.controller;

import com.hhplus.assignment.ecommerce.common.genericResponse.DataResponse;
import com.hhplus.assignment.ecommerce.common.genericResponse.ErrorResponse;
import com.hhplus.assignment.ecommerce.common.genericResponse.GenericResponse;
import com.hhplus.assignment.ecommerce.exception.EcommerceException;
import com.hhplus.assignment.ecommerce.wallet.controller.request.WalletRequestDto;
import com.hhplus.assignment.ecommerce.wallet.controller.response.WalletResponseDto;
import com.hhplus.assignment.ecommerce.wallet.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "지갑")
@RestController
@RequiredArgsConstructor
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    @Operation(summary = "회원 잔액 조회", description = "",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공",
                            content = @Content(schema = @Schema(implementation = WalletResponseDto.class))
					)
            })
    // 잔액 조회
    @GetMapping("/{memberId}")
    public GenericResponse getBalance(@PathVariable("memberId") Long memberId) {

        return DataResponse.create(walletService.getWallet(memberId));
    }

    @Operation(summary = "회원 잔액 충전", description = "",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = WalletRequestDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공",
                            content = @Content(schema = @Schema(implementation = WalletResponseDto.class))
                    ),
                    @ApiResponse(responseCode="404", description = "지갑이 존재하지 않는다.",
                            content = @Content(schema = @Schema(implementation = EcommerceException.class))
                    ),
            })
    // 잔액 충전
    @PatchMapping("")
    public GenericResponse chargeBalance(@RequestBody WalletRequestDto requestDto) {

        return DataResponse.create(walletService.chargeBalance(requestDto));
    }
}
