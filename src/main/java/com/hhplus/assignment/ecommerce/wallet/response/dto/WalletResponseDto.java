package com.hhplus.assignment.ecommerce.wallet.response.dto;

import com.hhplus.assignment.ecommerce.wallet.domain.entity.WalletEntity;

import java.math.BigDecimal;

public record WalletResponseDto(
        Long walletId,
        Long memberId,
        BigDecimal balance
) {
    public WalletResponseDto(WalletEntity entity) {
        this(entity.getId(), entity.getMemberId(), entity.getBalance());
    }
}
