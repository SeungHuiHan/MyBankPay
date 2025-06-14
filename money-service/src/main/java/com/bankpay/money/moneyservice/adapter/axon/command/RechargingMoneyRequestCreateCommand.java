package com.bankpay.money.moneyservice.adapter.axon.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

//충전 동작을 요청하는 것을 만드는 command
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RechargingMoneyRequestCreateCommand {
    @TargetAggregateIdentifier
    private String aggregateIdentifier;

    private String rechargingRequestId;

    private String membershipId;

    private int amount;

}
