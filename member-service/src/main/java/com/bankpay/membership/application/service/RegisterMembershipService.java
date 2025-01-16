package com.bankpay.membership.application.service;

import com.bankpay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.bankpay.membership.adapter.out.persistence.MembershipMapper;
import com.bankpay.membership.application.port.in.RegisterMembershipCommand;
import com.bankpay.membership.application.port.in.RegisterMembershipUseCase;
import com.bankpay.membership.application.port.out.RegisterMembershipPort;
import com.bankpay.membership.domain.Membership;
import common.UseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisterMembershipService implements RegisterMembershipUseCase {
    private final RegisterMembershipPort registerMembershipPort;
    private final MembershipMapper membershipMapper;

    @Override
    public Membership registerMembership(RegisterMembershipCommand command) {
        //???
        // command - >>DB

        //biz logic -> DB

        //external system
        // port, adapter

        MembershipJpaEntity jpaEntity=registerMembershipPort.createMembership(
                new Membership.MembershipName(command.getName()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipAddress(command.getAddress()),
                new Membership.MembershipIsValid(command.isValid()),
                new Membership.MembershipIsCorp(command.isCorp())
        );

        return membershipMapper.mapToDomainEntity(jpaEntity);
    }
}
