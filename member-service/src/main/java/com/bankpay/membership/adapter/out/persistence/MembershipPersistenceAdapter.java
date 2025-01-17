package com.bankpay.membership.adapter.out.persistence;

import com.bankpay.common.PersistenceAdapter;
import com.bankpay.membership.application.port.out.FindMembershipPort;
import com.bankpay.membership.application.port.out.ModifyMembershipPort;
import com.bankpay.membership.application.port.out.RegisterMembershipPort;
import com.bankpay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort , FindMembershipPort , ModifyMembershipPort {

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

    @Override
    public MembershipJpaEntity modifyMembership(Membership.MembershipId membershipId, Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp) {
        MembershipJpaEntity entity = membershipRepository.getById(Long.parseLong(membershipId.getMembershipId()));

        entity.setName(membershipName.getMembershipName());
        entity.setEmail(membershipEmail.getMembershipEmail());
        entity.setAddress(membershipAddress.getMembershipAddress());
        entity.setValid(membershipIsValid.isMembershipIsValid());
        entity.setCorp(membershipIsCorp.isMembershipIsCorp());
        return membershipRepository.save(entity);

    }
}
