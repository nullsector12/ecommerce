package com.hhplus.assignment.ecommerce.wallet.domain.repository;

import com.hhplus.assignment.ecommerce.exception.EcommerceException;
import com.hhplus.assignment.ecommerce.wallet.domain.entity.WalletEntity;
import com.hhplus.assignment.ecommerce.wallet.domain.jpaRepository.WalletJpaRepository;
import com.hhplus.assignment.ecommerce.wallet.domain.exception.WalletErrorCode;
import com.hhplus.assignment.ecommerce.wallet.service.dto.ChargeBalanceDto;
import com.hhplus.assignment.ecommerce.wallet.service.dto.WalletDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Repository
@RequiredArgsConstructor
public class WalletRepositoryImpl implements WalletRepository {

    private final WalletJpaRepository walletJpaRepository;

    @Override
    public WalletDto findWallet(Long memberId) {
        // 회원 지갑 조회 (없으면 exception)
        WalletEntity wallet = walletJpaRepository.findByMemberId(memberId)
                .orElseThrow(() -> EcommerceException.create(HttpStatus.NOT_FOUND
                        , WalletErrorCode.NOT_FOUND_MEMBERS_WALLET));
        return new WalletDto(wallet);
    }

    @Override
    @Transactional
    public WalletDto chargeBalance(ChargeBalanceDto requestDto) {
        // 회원 지갑 조회 (없으면 exception)
        WalletEntity wallet = walletJpaRepository.findByMemberIdForUpdate(requestDto.memberId())
                .orElseThrow(() -> EcommerceException.create(HttpStatus.NOT_FOUND
                        , WalletErrorCode.NOT_FOUND_MEMBERS_WALLET));
        // 지갑 잔액충전
        wallet.charge(requestDto.chargeAmount());
        return new WalletDto(wallet);
    }

    @Override
    public void useBalance(long walletId, BigDecimal usedAmount) {
        WalletEntity wallet = walletJpaRepository.findById(walletId)
                .orElseThrow(() -> EcommerceException.create(HttpStatus.NOT_FOUND
                        , WalletErrorCode.NOT_FOUND_WALLET_ID));

        wallet.useBalance(usedAmount);
    }

    @Override
    public WalletDto createWallet(Long memberId) {
        return new WalletDto(walletJpaRepository.save(WalletEntity.builder()
                .memberId(memberId)
                .balance(BigDecimal.ZERO)
                .build()));
    }

    @Override
    public void deleteAllWallets() {
        walletJpaRepository.deleteAll();
    }
}
