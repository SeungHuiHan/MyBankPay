package com.bankpay.banking.adapter.axon.event;

import lombok.*;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FirmbankingRequestUpdatedEvent {
    private int firmbankingStatus;
}
