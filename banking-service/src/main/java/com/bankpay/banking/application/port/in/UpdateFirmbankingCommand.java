package com.bankpay.banking.application.port.in;


import com.bankpay.common.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateFirmbankingCommand extends SelfValidating<RequestFirmbankingCommand> {
    @NotNull
    private final String firmbankingAggregateIdentifier;

    @NotNull
    private final int firmbankingStatus;

}
