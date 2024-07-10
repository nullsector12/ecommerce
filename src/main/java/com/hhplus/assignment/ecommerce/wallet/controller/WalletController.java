package com.hhplus.assignment.ecommerce.wallet.controller;

import com.hhplus.assignment.ecommerce.common.genericResponse.DataResponse;
import com.hhplus.assignment.ecommerce.common.genericResponse.ErrorResponse;
import com.hhplus.assignment.ecommerce.common.genericResponse.GenericResponse;
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
                            content = @Content(schema = @Schema(implementation = WalletResponseDto.class),
                                    examples = @ExampleObject(name = "정상응답", value = """
						{
							"data": {
								"walletId": 1,
								"memberId": 1,
								"balance": 100000
							}
						}
					"""))
					)
            })
    // 잔액 조회
    @GetMapping("/{memberId}")
    public GenericResponse getBalance(@PathVariable("memberId") Long memberId) {
//        return DataResponse.create(fakeWalletResponse.walletResponseDto);
        return DataResponse.create(walletService.getWallet(memberId));
    }

    @Operation(summary = "회원 잔액 충전", description = "",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = WalletRequestDto.class),
                            examples = {@ExampleObject(value = """
					{
					     "memberId": 1,
					     "chargeAmount": 1000
					 }
					""")
                            })
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공",
                            content = @Content(schema = @Schema(implementation = DataResponse.class),
                                    examples = @ExampleObject(name = "정상응답", value = """
						{	
							"data": {
								"walletId": 1,
								"memberId": 1,
								"balance": 100000
							}
						}
					"""))
                    ),
                    @ApiResponse(responseCode = "500", description = "내부 서버 오류",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                                    examples = {@ExampleObject(value = """
						{
						  "code": "internal_error",
						  "message": "알 수 없는 오류 발생"
						}
						""")})
                    ),
            })
    // 잔액 충전
    @PatchMapping
    public GenericResponse chargeBalance(@RequestBody WalletRequestDto requestDto) {
//        return DataResponse.create(fakeWalletResponse.walletResponseDto);
        return DataResponse.create(walletService.chargeBalance(requestDto));
    }
}
