package com.bankpay.money.moneyservice.application.port.in;

import com.bankpay.common.SelfValidating;
import io.axoniq.axonserver.grpc.command.Command;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class CreateMemberMoneyCommand extends SelfValidating<CreateMemberMoneyCommand>{
    @NotNull
    private final String membershipId;

    public CreateMemberMoneyCommand(@NotNull String targetMembershipId) {
        this.membershipId = targetMembershipId;
        this.validateSelf();
    }

}
