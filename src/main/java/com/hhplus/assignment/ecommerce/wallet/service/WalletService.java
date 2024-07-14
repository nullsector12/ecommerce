package com.hhplus.assignment.ecommerce.wallet.service;

import com.hhplus.assignment.ecommerce.wallet.controller.request.WalletRequestDto;
import com.hhplus.assignment.ecommerce.wallet.controller.response.WalletResponseDto;

import java.math.BigDecimal;

public interface WalletService {

    // 잔액 충전
    WalletResponseDto chargeBalance(WalletRequestDto requestDto);

    // 잔액 조회
    WalletResponseDto getWallet(Long memberId);

    // 잔액 사용
    WalletResponseDto useBalance(long walletId, BigDecimal usedAmount);

}
