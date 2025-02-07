package com.bankpay.banking.application.service;

import com.bankpay.banking.adapter.axon.command.CreateRegisteredBankAccountCommand;
import com.bankpay.banking.adapter.out.external.bank.BankAccount;
import com.bankpay.banking.adapter.out.external.bank.GetBankAccountRequest;
import com.bankpay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.bankpay.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import com.bankpay.banking.application.port.in.GetRegisteredBankAccountCommand;
import com.bankpay.banking.application.port.in.GetRegisteredBankAccountUseCase;
import com.bankpay.banking.application.port.in.RegisterBankAccountCommand;
import com.bankpay.banking.application.port.in.RegisterBankAccountUseCase;
import com.bankpay.banking.application.port.out.*;
import com.bankpay.banking.domain.RegisteredBankAccount;
import com.bankpay.common.UseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

@Service
@UseCase
@RequiredArgsConstructor
@Transactional
public class RegisterBankAccountService implements RegisterBankAccountUseCase, GetRegisteredBankAccountUseCase {
    private final GetMembershipPort getMembershipPort;
    private final RegisterBankAccountPort registerBankAccountPort;
    private final RegisteredBankAccountMapper registeredBankAccountMapper;
    private final RequestBankAccountInfoPort requestBankAccountInfoPort;
    private final GetRegisteredBankAccountPort getRegisteredBankAccountPort;
    private final CommandGateway commandGateway;

    @Override
    public RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command) {

        //은행 계좌를 등록해야하는 서비스 (비지니스 로직)
        //command.getMembershipId();

        // call membership service, 정상인지 확인
        // call external bank service, 정상인지 확인
        MembershipStatus membershipStatus =getMembershipPort.getMembership(command.getMembershipId());
        if(!membershipStatus.isValid()) {
            return null;
        }
        //1. 등록된 계좌인지 확인한다.
        //외부의 은행에 이 계좌가 정상인지? 확인해야한다
        //Biz Logic -> External System
        //Port -> Adapter -> External System

        command.getBankName();
        command.getBankAccountNumber();
        //실제 외부의 은행계좌 정보를 Get
        BankAccount accountInfo =requestBankAccountInfoPort.getBankAccountInfo(new GetBankAccountRequest(command.getBankName(),command.getBankAccountNumber()));

        boolean accountIsValid=accountInfo.isValid();

        //2. 등록가능한 계좌라면, 등록한다. 성공하면, 등록에 성공한 등록 정보를 리턴
        //2-1. 등록가능하지 않은 계좌라면, 에러를 리턴
        if (accountIsValid) {

            //등록 정보 저장
            RegisteredBankAccountJpaEntity savedAccountInfo=registerBankAccountPort.createRegisteredBankAccount(
                    new RegisteredBankAccount.MembershipId(command.getMembershipId()+""),
                    new RegisteredBankAccount.BankName(command.getBankName()),
                    new RegisteredBankAccount.BankAccountNumber(command.getBankAccountNumber()),
                    new RegisteredBankAccount.LinkedStatusIsValid(command.isLinkedStatusIsValid()),
                    new RegisteredBankAccount.AggregateIdentifier("")
            );

            return registeredBankAccountMapper.mapToDomainEntity(savedAccountInfo);

        }else{
            return null;
        }


    }

    @Override
    public void registerBankAccountByEvent(RegisterBankAccountCommand command) {
        commandGateway.send(new CreateRegisteredBankAccountCommand(command.getMembershipId(), command.getBankName(), command.getBankAccountNumber()))
                .whenComplete(
                (result, throwable) -> {
                    if(throwable != null) {
                        System.out.println("throwable = " + throwable);
                    }
                    //정상적으로 이벤트 소싱. -> registerBankAccount를 insert
                    registerBankAccountPort.createRegisteredBankAccount(
                            new RegisteredBankAccount.MembershipId(command.getMembershipId()+""),
                            new RegisteredBankAccount.BankName(command.getBankName()),
                            new RegisteredBankAccount.BankAccountNumber(command.getBankAccountNumber()),
                            new RegisteredBankAccount.LinkedStatusIsValid(command.isLinkedStatusIsValid()),
                            new RegisteredBankAccount.AggregateIdentifier(result.toString())
                    );
                }
        );
    }

    @Override
    public RegisteredBankAccount getRegisteredBankAccount(GetRegisteredBankAccountCommand command) {
        return registeredBankAccountMapper.mapToDomainEntity(getRegisteredBankAccountPort.getRegisteredBankAccount(command));

    }
}
