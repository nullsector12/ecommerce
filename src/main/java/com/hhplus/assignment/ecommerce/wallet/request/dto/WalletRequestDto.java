package com.hhplus.assignment.ecommerce.wallet.request.dto;

import java.math.BigDecimal;

public record WalletRequestDto(
        Long memberId,
        BigDecimal chargeAmount
) {
}
