package com.hhplus.assignment.ecommerce.wallet.service.dto;

import com.hhplus.assignment.ecommerce.wallet.domain.entity.WalletEntity;

import java.math.BigDecimal;

public record WalletDto(
        Long walletId,
        Long memberId,
        BigDecimal balance
) {

    public WalletDto(WalletEntity entity) {
        this(entity.getId(), entity.getMemberId(), entity.getBalance());
    }
}
