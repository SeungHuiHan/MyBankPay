package com.bankpay.money.moneyservice.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataMemberMoneyRepository extends JpaRepository<MemberMoneyJpaEntity, Long> {

    @Query("SELECT e  FROM MemberMoneyJpaEntity e WHERE e.membershipId = :membershipId")
    List<MemberMoneyJpaEntity> findByMembershipId(@Param("membershipId") Long membershipId);

    @Query("SELECT e  FROM MemberMoneyJpaEntity e WHERE e.membershipId in :membershipIds")
    List<MemberMoneyJpaEntity> fineMemberMoneyListByMembershipIds(@Param("membershipIds") List<Long> membershipIds);
}
