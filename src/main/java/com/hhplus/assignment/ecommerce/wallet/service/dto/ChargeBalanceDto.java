package com.hhplus.assignment.ecommerce.wallet.service.dto;

import com.hhplus.assignment.ecommerce.wallet.controller.request.WalletRequestDto;

import java.math.BigDecimal;

public record ChargeBalanceDto(
        Long memberId,
        BigDecimal chargeAmount
) {
    public ChargeBalanceDto(WalletRequestDto requestDto) {
        this(requestDto.memberId(), requestDto.chargeAmount());
    }
}
