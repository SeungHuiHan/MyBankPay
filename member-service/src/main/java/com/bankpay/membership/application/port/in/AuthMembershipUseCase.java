package com.bankpay.membership.application.port.in;

import com.bankpay.membership.domain.JwtToken;
import com.bankpay.membership.domain.Membership;

public interface AuthMembershipUseCase {
    JwtToken loginMembership(LoginMembershipCommand command);

    JwtToken refreshJwtTokenByRefreshToken(RefreshTokenCommand command);
    boolean validateJwtToken(ValidateTokenCommand command);

    Membership getMembershipByJwtToken(ValidateTokenCommand command);
}
