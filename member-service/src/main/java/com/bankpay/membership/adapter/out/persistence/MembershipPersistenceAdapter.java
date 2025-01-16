package com.bankpay.membership.adapter.out.persistence;

import com.bankpay.membership.application.port.out.FindMembershipPort;
import com.bankpay.membership.application.port.out.RegisterMembershipPort;
import com.bankpay.membership.domain.Membership;
import common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort , FindMembershipPort {

    private final SpringDataMembershipRepository membershipRepository;
    @Override
    public MembershipJpaEntity createMembership(Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp) {
        return membershipRepository.save(new MembershipJpaEntity(
                membershipName.getMembershipName(),
                membershipEmail.getMembershipEmail(),
                membershipAddress.getMembershipAddress(),
                membershipIsValid.isMembershipIsValid(),
                membershipIsCorp.isMembershipIsCorp()
        ));
    }

    @Override
    public MembershipJpaEntity findMembership(Membership.MembershipId membership) {
        return membershipRepository.getById(Long.parseLong(membership.getMembershipId()));
    }
}
