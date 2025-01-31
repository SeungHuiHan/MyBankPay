package com.bankpay.banking.adapter.axon.command;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateFirmbankingRequestCommand {

    @NotNull
    @TargetAggregateIdentifier
    private String aggregateIdentifier;

    private int firmbankingStatus;

}
