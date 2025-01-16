package com.bankpay.membership.application.port.in;

import com.bankpay.membership.domain.Membership;
import common.UseCase;


public interface RegisterMembershipUseCase {
    Membership registerMembership(RegisterMembershipCommand command);
}
