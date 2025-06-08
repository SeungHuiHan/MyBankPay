package com.bankpay.membership.application.port.in;

import com.bankpay.membership.domain.Membership;

public interface ModifyMembershipUseCase {
    Membership modifyMembership(ModifyMembershipCommand command);

}
