package com.hhplus.assignment.ecommerce.wallet.service;

import com.hhplus.assignment.ecommerce.exception.EcommerceException;
import com.hhplus.assignment.ecommerce.wallet.controller.request.WalletRequestDto;
import com.hhplus.assignment.ecommerce.wallet.domain.repository.WalletRepository;
import com.hhplus.assignment.ecommerce.wallet.controller.response.WalletResponseDto;
import com.hhplus.assignment.ecommerce.wallet.service.dto.ChargeBalanceDto;
import com.hhplus.assignment.ecommerce.wallet.service.dto.WalletDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    private WalletDto walletDto;
    private WalletRequestDto walletRequestDto;

    @BeforeEach
    void setUp() {
        walletDto = new WalletDto(1L, 1L,new BigDecimal(1000));
        walletRequestDto = new WalletRequestDto(1L, new BigDecimal(1000));
    }

    @Test
    @DisplayName("회원 잔액사용 테스트 - 잔액부족 예외 처리")
    void useBalance() {
        // given
        long walletId = 1L;
        BigDecimal usedAmount = new BigDecimal(1500);
        WalletDto walletDto = new WalletDto(walletId, 1L, new BigDecimal(1000));
        when(walletRepository.findWallet(walletId)).thenReturn(walletDto);

        // when/then
        assertThrows(EcommerceException.class, () -> walletService.useBalance(walletId, usedAmount));
    }

    @Test
    @DisplayName("회원 잔액확인 테스트")
    void getBalance() {
        // given
        when(walletRepository.findWallet(1L)).thenReturn(walletDto);

        // when
        WalletResponseDto response = walletService.getWallet(1L);

        // then
        assertEquals(response.walletId(), walletDto.walletId());
        assertEquals(response.memberId(), walletDto.memberId());
        assertEquals(response.balance(), walletDto.balance());
    }

    @Test
    @DisplayName("회원 잔액충전 테스트")
    void chargeBalance() {
        // given
        long memberId = 1L;
        ChargeBalanceDto chargeBalanceDto = new ChargeBalanceDto(memberId, new BigDecimal(1000));
        WalletDto walletDtoResult = new WalletDto(1L, memberId, new BigDecimal(2000));
        when(walletRepository.chargeBalance(chargeBalanceDto)).thenReturn(walletDtoResult);

        // when
        WalletResponseDto response = walletService.chargeBalance(walletRequestDto);

        // then
        assertEquals(response, new WalletResponseDto(walletDtoResult));
    }
}
