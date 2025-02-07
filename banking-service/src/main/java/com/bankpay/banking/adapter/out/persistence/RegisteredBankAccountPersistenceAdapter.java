package com.bankpay.banking.adapter.out.persistence;

import com.bankpay.banking.application.port.in.GetRegisteredBankAccountCommand;
import com.bankpay.banking.application.port.out.FindBankAccountPort;
import com.bankpay.banking.application.port.out.GetRegisteredBankAccountPort;
import com.bankpay.banking.application.port.out.RegisterBankAccountPort;
import com.bankpay.banking.domain.RegisteredBankAccount;
import com.bankpay.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisteredBankAccountPersistenceAdapter implements RegisterBankAccountPort, FindBankAccountPort, GetRegisteredBankAccountPort {

    private final SpringDataRegisteredBankAccountRepository registeredBankAccountRepository;

    @Override
    public RegisteredBankAccountJpaEntity createRegisteredBankAccount(RegisteredBankAccount.MembershipId membershipId, RegisteredBankAccount.BankName bankName, RegisteredBankAccount.BankAccountNumber bankAccountNumber,
                                                                      RegisteredBankAccount.LinkedStatusIsValid linkedStatusIsValid,RegisteredBankAccount.AggregateIdentifier aggregateIdentifier) {
        return registeredBankAccountRepository.save(new RegisteredBankAccountJpaEntity(
                membershipId.getMembershipId(),
                bankName.getBankName(),
                bankAccountNumber.getBankAccountNumber(),
                linkedStatusIsValid.isLinkedStatusIsValid(),
                aggregateIdentifier.getAggregateIdentifier()
        ));
    }

    @Override
    public RegisteredBankAccountJpaEntity findRegisteredBankAccount(RegisteredBankAccount.RegisteredBankAccountId registeredBankAccount) {
        return registeredBankAccountRepository.getById(Long.parseLong(registeredBankAccount.getRegisteredBankAccountId()));
    }

    @Override
    public RegisteredBankAccountJpaEntity getRegisteredBankAccount(GetRegisteredBankAccountCommand command) {
        List<RegisteredBankAccountJpaEntity> entityList = registeredBankAccountRepository.findByMembershipId(command.getMembershipId());
        if (entityList.size() > 0) {
            return entityList.get(0);
        }
        return null;
    }
}
