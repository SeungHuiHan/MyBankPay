package com.bankpay.banking.application.port.in;

import com.bankpay.banking.domain.RegisteredBankAccount;

public interface FindBankAccountUseCase {
    RegisteredBankAccount findRegisteredBankAccount(FindBankAccountCommand command);
}
