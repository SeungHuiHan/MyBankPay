package com.bankpay.membership.application.port.in;

import com.bankpay.membership.domain.Membership;

public interface FindMembershipUseCase {
    Membership findMembership(FindMembershipCommand command);
}
