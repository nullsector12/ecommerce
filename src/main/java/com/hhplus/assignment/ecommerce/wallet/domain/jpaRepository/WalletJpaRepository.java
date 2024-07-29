package com.hhplus.assignment.ecommerce.wallet.domain.jpaRepository;

import com.hhplus.assignment.ecommerce.wallet.domain.entity.WalletEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletJpaRepository extends JpaRepository<WalletEntity, Long> {
    Optional<WalletEntity> findByMemberId(Long memberId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select w from WalletEntity w where w.memberId = :memberId")
    Optional<WalletEntity> findByMemberIdForUpdate(@Param("memberId") Long memberId);
}
