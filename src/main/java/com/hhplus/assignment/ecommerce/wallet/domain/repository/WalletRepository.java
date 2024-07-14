package com.hhplus.assignment.ecommerce.wallet.domain.repository;

import com.hhplus.assignment.ecommerce.wallet.service.dto.ChargeBalanceDto;
import com.hhplus.assignment.ecommerce.wallet.service.dto.WalletDto;

import java.math.BigDecimal;

public interface WalletRepository {

    // 회원 지갑 조회
    WalletDto findWallet(Long memberId);

    // 잔액 충전
    WalletDto chargeBalance(ChargeBalanceDto requestDto);

    WalletDto useBalance(long walletId, BigDecimal usedAmount);
}
