package com.hhplus.assignment.ecommerce.wallet.controller.response;

import com.hhplus.assignment.ecommerce.wallet.domain.entity.WalletEntity;
import com.hhplus.assignment.ecommerce.wallet.service.dto.WalletDto;

import java.math.BigDecimal;

public record WalletResponseDto(
        Long walletId,
        Long memberId,
        BigDecimal balance
) {
    public WalletResponseDto(WalletDto walletDto) {
        this(walletDto.walletId(), walletDto.memberId(), walletDto.balance());
    }
}
