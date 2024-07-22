package com.hhplus.assignment.ecommerce.wallet.controller;

import com.hhplus.assignment.ecommerce.exception.EcommerceException;
import com.hhplus.assignment.ecommerce.wallet.controller.response.WalletResponseDto;
import com.hhplus.assignment.ecommerce.wallet.domain.repository.WalletRepository;
import com.hhplus.assignment.ecommerce.wallet.domain.exception.WalletErrorCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WalletControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WalletRepository walletRepository;

    @BeforeEach
    public void setUp() {
        walletRepository.createWallet(1L);
    }

    @AfterEach
    public void tearDown() {
        walletRepository.deleteAllWallets();
    }

    @Test
    @DisplayName("잔액 조회 - 지갑이 존재하지 않을 때, 지갑을 찾을 수 없다는 에러 메시지를 반환한다.")
    void notFoundWallet() {
        // given
        long memberId = 2L;
        String url = "http://localhost:" + port + "/wallet/" + memberId;

        // when
        ResponseEntity<WalletResponseDto> response = restTemplate.getForEntity(url, WalletResponseDto.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThatExceptionOfType(EcommerceException.class).isThrownBy(() -> {
           throw EcommerceException.create(HttpStatus.NOT_FOUND, WalletErrorCode.NOT_FOUND_MEMBERS_WALLET);
        });
    }

    @Test
    @DisplayName("잔액 조회 - 지갑이 존재할 때, 잔액을 찾을 수 있다.")
    void findMemberWallet() {
        // given
        long memberId = 1L;
        String url = "http://localhost:" + port + "/wallet/" + memberId;

        // when
        ResponseEntity<WalletResponseDto> response = restTemplate.getForEntity(url, WalletResponseDto.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


}
