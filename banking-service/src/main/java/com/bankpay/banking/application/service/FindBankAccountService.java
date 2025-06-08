package com.bankpay.banking.application.service;

import com.bankpay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.bankpay.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import com.bankpay.banking.application.port.in.FindBankAccountCommand;
import com.bankpay.banking.application.port.in.FindBankAccountUseCase;
import com.bankpay.banking.application.port.out.FindBankAccountPort;
import com.bankpay.banking.domain.RegisteredBankAccount;
import com.bankpay.common.UseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@UseCase
@RequiredArgsConstructor
@Transactional
public class FindBankAccountService implements FindBankAccountUseCase {
    private final FindBankAccountPort findBankAccountPort;
    private final RegisteredBankAccountMapper registeredBankAccountMapper;

    @Override
    public RegisteredBankAccount findRegisteredBankAccount(FindBankAccountCommand command) {
        RegisteredBankAccountJpaEntity entity=findBankAccountPort.findRegisteredBankAccount(new RegisteredBankAccount.RegisteredBankAccountId(command.getRegisteredBankAccountId()));
        return registeredBankAccountMapper.mapToDomainEntity(entity);
    }
}
