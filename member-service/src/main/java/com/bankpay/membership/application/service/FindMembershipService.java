package com.bankpay.membership.application.service;

import com.bankpay.common.UseCase;
import com.bankpay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.bankpay.membership.adapter.out.persistence.MembershipMapper;
import com.bankpay.membership.application.port.in.FindMembershipCommand;
import com.bankpay.membership.application.port.in.FindMembershipListByAddressCommand;
import com.bankpay.membership.application.port.in.FindMembershipUseCase;
import com.bankpay.membership.application.port.out.FindMembershipPort;
import com.bankpay.membership.domain.Membership;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@UseCase
@RequiredArgsConstructor
@Transactional
public class FindMembershipService implements FindMembershipUseCase {
    private final FindMembershipPort findMembershipPort;
    private final MembershipMapper membershipMapper;
    @Override
    public Membership findMembership(FindMembershipCommand command) {
        MembershipJpaEntity entity=findMembershipPort.findMembership(new Membership.MembershipId(command.getMembershipId()));
        return membershipMapper.mapToDomainEntity(entity);
    }


    @Override
    public List<Membership> findMembershipListByAddress(FindMembershipListByAddressCommand command) {
        List<MembershipJpaEntity> membershipJpaEntities = findMembershipPort.findMembershipListByAddress(new Membership.MembershipAddress(command.getAddressName()));
        List<Membership> memberships = new ArrayList<>();

        for (MembershipJpaEntity membershipJpaEntity : membershipJpaEntities) {
            memberships.add(membershipMapper.mapToDomainEntity(membershipJpaEntity));
        }
        return memberships;
    }
}
