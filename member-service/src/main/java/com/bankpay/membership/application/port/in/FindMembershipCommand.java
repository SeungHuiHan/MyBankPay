package com.bankpay.membership.application.port.in;

import com.bankpay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper=false)
public class FindMembershipCommand extends SelfValidating<FindMembershipCommand> {
    private final String membershipId;
}
