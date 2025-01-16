package com.bankpay.membership.adapter.out.persistence;

import com.bankpay.membership.domain.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataMembershipRepository extends JpaRepository<MembershipJpaEntity, Long> {

}
