package com.hhplus.assignment.ecommerce.wallet.response;

import com.hhplus.assignment.ecommerce.wallet.domain.entity.WalletEntity;
import com.hhplus.assignment.ecommerce.wallet.response.dto.WalletResponseDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FakeWalletResponse {

    public WalletResponseDto walletResponseDto = getWalletBalance();

    private WalletResponseDto getWalletBalance() {
        WalletEntity walletEntity = new WalletEntity(1L, 1L, BigDecimal.valueOf(10000000));

        return new WalletResponseDto(walletEntity);
    }
}
