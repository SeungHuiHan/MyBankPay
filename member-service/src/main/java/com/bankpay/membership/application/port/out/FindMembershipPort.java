package com.bankpay.membership.application.port.out;

import com.bankpay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.bankpay.membership.domain.Membership;

import java.util.List;

public interface FindMembershipPort {

    MembershipJpaEntity findMembership(Membership.MembershipId membership);

    List<MembershipJpaEntity> findMembershipListByAddress(
            Membership.MembershipAddress membershipAddress
    );
}
