package com.bankpay.banking.application.port.out;

import com.bankpay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.bankpay.banking.domain.RegisteredBankAccount;

public interface FindBankAccountPort {

    RegisteredBankAccountJpaEntity findRegisteredBankAccount(RegisteredBankAccount.RegisteredBankAccountId registeredBankAccount);
}
