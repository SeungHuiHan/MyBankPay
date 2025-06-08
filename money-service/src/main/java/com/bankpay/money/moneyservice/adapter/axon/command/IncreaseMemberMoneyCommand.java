package com.bankpay.money.moneyservice.adapter.axon.command;

import com.bankpay.common.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class IncreaseMemberMoneyCommand extends SelfValidating<MemberMoneyCreatedCommand> {

    @NotNull
    @TargetAggregateIdentifier
    private String aggregateIdentifier; //axon이 이 Identifier를 가지는 aggregate를 변경시키기 위함
    @NotNull
    private final String membershipId;
    @NotNull
    private final int amount;
}
