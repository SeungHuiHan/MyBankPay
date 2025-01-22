package com.bankpay.banking.adapter.out.persistence;

import com.bankpay.banking.application.port.in.FindBankAccountUseCase;
import com.bankpay.banking.application.port.out.FindBankAccountPort;
import com.bankpay.banking.application.port.out.RegisterBankAccountPort;
import com.bankpay.banking.domain.RegisteredBankAccount;
import com.bankpay.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.processing.Find;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisteredBankAccountPersistenceAdapter implements RegisterBankAccountPort, FindBankAccountPort {

    private final SpringDataRegisteredBankAccountRepository registeredBankAccountRepository;

    @Override
    public RegisteredBankAccountJpaEntity createRegisteredBankAccount(RegisteredBankAccount.MembershipId membershipId, RegisteredBankAccount.BankName bankName, RegisteredBankAccount.BankAccountNumber bankAccountNumber, RegisteredBankAccount.LinkedStatusIsValid linkedStatusIsValid) {
        return registeredBankAccountRepository.save(new RegisteredBankAccountJpaEntity(
                membershipId.getMembershipId(),
                bankName.getBankName(),
                bankAccountNumber.getBankAccountNumber(),
                linkedStatusIsValid.isLinkedStatusIsValid()
        ));
    }

    @Override
    public RegisteredBankAccountJpaEntity findRegisteredBankAccount(RegisteredBankAccount.RegisteredBankAccountId registeredBankAccount) {
        return registeredBankAccountRepository.getById(Long.parseLong(registeredBankAccount.getRegisteredBankAccountId()));
    }
}
