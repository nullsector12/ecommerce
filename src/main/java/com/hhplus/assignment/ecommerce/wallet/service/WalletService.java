package com.hhplus.assignment.ecommerce.wallet.service;

import com.hhplus.assignment.ecommerce.wallet.domain.repository.WalletRepository;
import com.hhplus.assignment.ecommerce.wallet.controller.response.WalletResponseDto;
import com.hhplus.assignment.ecommerce.wallet.controller.request.WalletRequestDto;
import com.hhplus.assignment.ecommerce.wallet.service.dto.ChargeBalanceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    // 잔액 충전
    @Transactional
    public WalletResponseDto chargeBalance(WalletRequestDto requestDto) {
        return new WalletResponseDto(walletRepository.chargeBalance(new ChargeBalanceDto(requestDto)));
    }

    // 잔액 조회
    public WalletResponseDto getWallet(Long memberId) {
        return new WalletResponseDto(walletRepository.findWallet(memberId));
    }

    @Transactional
    public void useBalance(long walletId, BigDecimal usedAmount) {
        walletRepository.useBalance(walletId, usedAmount);
    }
}
