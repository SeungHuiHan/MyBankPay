package com.bankpay.banking.application.port.out;

import com.bankpay.banking.adapter.out.external.bank.BankAccount;
import com.bankpay.banking.adapter.out.external.bank.GetBankAccountRequest;

public interface RequestBankAccountInfoPort {
    BankAccount getBankAccountInfo(GetBankAccountRequest request);
}
