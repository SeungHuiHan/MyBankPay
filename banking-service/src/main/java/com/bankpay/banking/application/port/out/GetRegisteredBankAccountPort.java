package com.bankpay.banking.application.port.out;

import com.bankpay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.bankpay.banking.application.port.in.GetRegisteredBankAccountCommand;

public interface GetRegisteredBankAccountPort {
    RegisteredBankAccountJpaEntity getRegisteredBankAccount(GetRegisteredBankAccountCommand command);
}
