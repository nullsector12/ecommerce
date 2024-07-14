package com.hhplus.assignment.ecommerce.wallet.domain.repository;

import com.hhplus.assignment.ecommerce.common.model.CommonErrorCode;
import com.hhplus.assignment.ecommerce.exception.EcommerceException;
import com.hhplus.assignment.ecommerce.wallet.domain.entity.WalletEntity;
import com.hhplus.assignment.ecommerce.wallet.controller.response.WalletResponseDto;
import com.hhplus.assignment.ecommerce.wallet.domain.jpaRepository.WalletJpaRepository;
import com.hhplus.assignment.ecommerce.wallet.controller.request.WalletRequestDto;
import com.hhplus.assignment.ecommerce.wallet.service.dto.ChargeBalanceDto;
import com.hhplus.assignment.ecommerce.wallet.service.dto.WalletDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Slf4j
@Repository
@RequiredArgsConstructor
public class WalletRepositoryImpl implements WalletRepository {

    private final WalletJpaRepository walletJpaRepository;

    @Override
    public WalletDto findWallet(Long memberId) {
        // 회원 지갑 조회 (없으면 exception)
        WalletEntity wallet = walletJpaRepository.findById(memberId)
                .orElseThrow(() -> new EcommerceException(HttpStatus.NOT_FOUND
                        , CommonErrorCode.NOT_FOUND.getCode(), CommonErrorCode.NOT_FOUND.getMessage()));
        return new WalletDto(wallet);
    }

    @Override
    public WalletDto chargeBalance(ChargeBalanceDto requestDto) {
        // 회원 지갑 조회 (없으면 exception)
        WalletEntity wallet = walletJpaRepository.findById(requestDto.memberId())
                .orElseThrow(() -> new EcommerceException(HttpStatus.NOT_FOUND
                        , CommonErrorCode.NOT_FOUND.getCode(), CommonErrorCode.NOT_FOUND.getMessage()));
        // 지갑 잔액충전
        wallet.charge(requestDto.chargeAmount());
        return new WalletDto(wallet);
    }

    @Override
    public WalletDto useBalance(long walletId, BigDecimal usedAmount) {
        WalletEntity wallet = walletJpaRepository.findById(walletId)
                .orElseThrow(() -> new EcommerceException(HttpStatus.NOT_FOUND
                        , CommonErrorCode.NOT_FOUND.getCode(), CommonErrorCode.NOT_FOUND.getMessage()));

        wallet.useBalance(usedAmount);
        return new WalletDto(wallet);
    }
}
