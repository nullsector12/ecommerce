package com.hhplus.assignment.ecommerce.wallet.controller.request;

import java.math.BigDecimal;

public record WalletRequestDto(
        Long memberId,
        BigDecimal chargeAmount
) {
}
