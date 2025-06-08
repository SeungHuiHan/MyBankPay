package com.bankpay.banking.application.port.in;

import com.bankpay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper=false)
public class FindBankAccountCommand extends SelfValidating<FindBankAccountCommand> {
    private final String registeredBankAccountId;
}
