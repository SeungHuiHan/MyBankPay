package com.bankpay.banking.application.port.in;

import com.bankpay.banking.domain.RegisteredBankAccount;


public interface RegisterBankAccountUseCase {
    RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command);
}
