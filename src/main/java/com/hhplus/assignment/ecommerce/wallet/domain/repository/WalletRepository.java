package com.hhplus.assignment.ecommerce.wallet.domain.repository;

import com.hhplus.assignment.ecommerce.wallet.service.dto.ChargeBalanceDto;
import com.hhplus.assignment.ecommerce.wallet.service.dto.WalletDto;

import java.math.BigDecimal;

public interface WalletRepository {

    // 회원 지갑 조회
    WalletDto findWallet(Long memberId);

    // 잔액 충전
    WalletDto chargeBalance(ChargeBalanceDto requestDto);

    // 잔액 사용
    void useBalance(long walletId, BigDecimal usedAmount);

    // 회원 지갑 생성
    WalletDto createWallet(Long memberId);

    // 회원 지갑 전체 삭제
    void deleteAllWallets();

}
