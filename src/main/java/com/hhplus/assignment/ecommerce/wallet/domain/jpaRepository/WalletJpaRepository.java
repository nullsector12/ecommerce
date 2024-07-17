package com.hhplus.assignment.ecommerce.wallet.domain.jpaRepository;

import com.hhplus.assignment.ecommerce.wallet.domain.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletJpaRepository extends JpaRepository<WalletEntity, Long> {
    Optional<WalletEntity> findByMemberId(Long memberId);
}
