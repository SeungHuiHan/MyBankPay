package com.bankpay.banking.adapter.axon.aggregate;

import com.bankpay.banking.adapter.axon.command.CreateRegisteredBankAccountCommand;
import com.bankpay.banking.adapter.axon.event.CreateRegisteredBankAccountEvent;
import com.bankpay.banking.adapter.out.external.bank.BankAccount;
import com.bankpay.banking.adapter.out.external.bank.GetBankAccountRequest;
import com.bankpay.banking.application.port.out.RequestBankAccountInfoPort;
import com.bankpay.common.command.CheckRegisteredBankAccountCommand;
import com.bankpay.common.event.CheckedRegisteredBankAccountEvent;
import jakarta.validation.constraints.NotNull;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate()
public class RegisteredBankAccountAggregate {
    @AggregateIdentifier
    private String id;

    private String membershipId;

    private String bankName;

    private String bankAccountNumber;

    @CommandHandler
    public RegisteredBankAccountAggregate(@NotNull CreateRegisteredBankAccountCommand command) {
        System.out.println("CreateRegisteredBankAccountCommand Handler");

        // store event
        apply(new CreateRegisteredBankAccountEvent(command.getMembershipId(), command.getBankName(), command.getBankAccountNumber()));
    }

    @CommandHandler
    public void handle(@NotNull CheckRegisteredBankAccountCommand command, RequestBankAccountInfoPort bankAccountInfoPort) {
        System.out.println("CheckRegisteredBankAccountCommand Handler");

        // command 를 통해, 이 어그리거트(RegisteredBankAccount) 가 정상인지를 확인해야해요.
        id = command.getAggregateIdentifier();

        // Check! Registerd Bank Account
        BankAccount account = bankAccountInfoPort.getBankAccountInfo(new GetBankAccountRequest(command.getBankName(), command.getBankAccountNumber()));
        boolean isValidAccount = account.isValid(); //계좌번호 정상인지 확인
        // 🚨 여기가 실행되는지 로그 추가 필요!
        String firmbankingUUID = UUID.randomUUID().toString();

        // CheckedRegisteredBankAccountEvent
        apply(new CheckedRegisteredBankAccountEvent(
                        command.getRechargeRequestId()
                        , command.getCheckRegisteredBankAccountId()
                        , command.getMembershipId()
                        , isValidAccount
                        , command.getAmount()
                        , firmbankingUUID
                        , account.getBankName()
                        , account.getBankAccountNumber()
                )
        );

    }

    @EventSourcingHandler
    public void on(CreateRegisteredBankAccountEvent event) {
        System.out.println("CreateRegisteredBankAccountEvent Sourcing Handler");
        id = UUID.randomUUID().toString();
        membershipId = event.getMembershipId();
        bankName = event.getBankName();
        bankAccountNumber = event.getBankAccountNumber();

    }

    public RegisteredBankAccountAggregate() {
        // System.out.println("RegisteredBankAccountAggregate Constructor");
    }
}
