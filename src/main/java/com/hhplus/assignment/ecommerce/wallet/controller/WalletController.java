package com.hhplus.assignment.ecommerce.wallet.controller;

import com.hhplus.assignment.ecommerce.common.genericResponse.DataResponse;
import com.hhplus.assignment.ecommerce.common.genericResponse.GenericResponse;
import com.hhplus.assignment.ecommerce.wallet.request.dto.WalletRequestDto;
import com.hhplus.assignment.ecommerce.wallet.response.FakeWalletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallet")
public class WalletController {

    private final FakeWalletResponse fakeWalletResponse;

    // 잔액 조회
    @GetMapping("/{memberId}")
    public GenericResponse getBalance(@PathVariable("memberId") Long memberId) {
        return DataResponse.create(fakeWalletResponse.walletResponseDto);
    }

    // 잔액 충전
    @PostMapping
    public GenericResponse chargeBalance(@RequestBody WalletRequestDto requestDto) {
        return DataResponse.create(fakeWalletResponse.walletResponseDto);
    }
}
