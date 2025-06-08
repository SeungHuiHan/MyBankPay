package com.bankpay.membership.application.service;

import com.bankpay.common.UseCase;
import com.bankpay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.bankpay.membership.adapter.out.persistence.MembershipMapper;
import com.bankpay.membership.application.port.in.ModifyMembershipCommand;
import com.bankpay.membership.application.port.in.ModifyMembershipUseCase;
import com.bankpay.membership.application.port.out.ModifyMembershipPort;
import com.bankpay.membership.domain.Membership;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@UseCase
@RequiredArgsConstructor
@Transactional
public class ModifyMembershipService implements ModifyMembershipUseCase {
    private final ModifyMembershipPort modifyMembershipPort;
    private final MembershipMapper membershipMapper;
    @Override
    public Membership modifyMembership(ModifyMembershipCommand command) {
        MembershipJpaEntity jpaEntity=modifyMembershipPort.modifyMembership(
                new Membership.MembershipId(command.getMembershipId()),
                new Membership.MembershipName(command.getName()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipAddress(command.getAddress()),
                new Membership.MembershipIsValid(command.isValid()),
                new Membership.MembershipIsCorp(command.isCorp()),
                new Membership.MembershipRefreshToken("")
        );

        return membershipMapper.mapToDomainEntity(jpaEntity);
    }
}
