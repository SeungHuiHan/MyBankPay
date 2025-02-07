package com.bankpay.banking.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataRegisteredBankAccountRepository extends JpaRepository<RegisteredBankAccountJpaEntity, Long> {

    @Query("SELECT e  FROM RegisteredBankAccountJpaEntity e WHERE e.membershipId = :membershipId")
    List<RegisteredBankAccountJpaEntity> findByMembershipId(@Param("membershipId") String membershipId);

}
