package com.bankpay.membership.adapter.out.persistence;

import com.bankpay.membership.domain.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataMembershipRepository extends JpaRepository<MembershipJpaEntity, Long> {

    @Query("SELECT e  FROM MembershipJpaEntity e WHERE e.address = :address")
    List<MembershipJpaEntity> findByAddress(@Param("address") String address);

}
