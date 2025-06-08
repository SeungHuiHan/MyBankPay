package com.bankpay.money.moneyservice.adapter.axon.aggregate;

import com.bankpay.money.moneyservice.adapter.axon.command.IncreaseMemberMoneyCommand;
import com.bankpay.money.moneyservice.adapter.axon.command.MemberMoneyCreatedCommand;
import com.bankpay.money.moneyservice.adapter.axon.command.RechargingMoneyRequestCreateCommand;
import com.bankpay.money.moneyservice.adapter.axon.event.IncreaseMemberMoneyEvent;
import com.bankpay.money.moneyservice.adapter.axon.event.MemberMoneyCreatedEvent;
import com.bankpay.money.moneyservice.adapter.axon.event.RechargingRequestCreatedEvent;
import com.bankpay.money.moneyservice.application.port.out.GetRegisteredBankAccountPort;
import com.bankpay.money.moneyservice.application.port.out.RegisteredBankAccountAggregateIdentifier;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate()
@Data
public class MemberMoneyAggregate {  //aggregate를 변경시키려면 이 클래스를 통해서만 가능
    @AggregateIdentifier
    private String id;

    private Long membershipId;

    private int balance;

    //command를 핸들링하는 생성자가 command를 받아서 다시 이벤트를 만듦
    @CommandHandler
    public MemberMoneyAggregate(MemberMoneyCreatedCommand command) {
        System.out.println("MemberMoneyCreatedCommand Handler");

        apply(new MemberMoneyCreatedEvent(command.getMembershipId()));
    }

    @CommandHandler
    public String handle(@NotNull IncreaseMemberMoneyCommand command){
        System.out.println("IncreaseMemberMoneyCommand Handler");
        id = command.getAggregateIdentifier();

        // store event
        apply(new IncreaseMemberMoneyEvent(id, command.getMembershipId(), command.getAmount()));
        return id;
    }

    @CommandHandler
    public void handler(RechargingMoneyRequestCreateCommand command, GetRegisteredBankAccountPort getRegisteredBankAccountPort){
        System.out.println("RechargingMoneyRequestCreateCommand Handler");
        id = command.getAggregateIdentifier();

        //Sage Start
        // new RechargingRequestCreatedEvent
        // banking 정보가 필요해요. -> bank svc (get RegisteredBankAccount) 를 위한. Port.
        RegisteredBankAccountAggregateIdentifier registeredBankAccountAggregateIdentifier
                = getRegisteredBankAccountPort.getRegisteredBankAccount(command.getMembershipId());


        // Saga Start
        apply(new RechargingRequestCreatedEvent(
                command.getRechargingRequestId(),
                command.getMembershipId(),
                command.getAmount(),
                registeredBankAccountAggregateIdentifier.getAggregateIdentifier(),
                registeredBankAccountAggregateIdentifier.getBankName(),
                registeredBankAccountAggregateIdentifier.getBankAccountNumber()
        ));
    }

    //이벤트를 받아서 MemberMoneyAggregate 생성
    @EventSourcingHandler
    public void on(MemberMoneyCreatedEvent event) {
        System.out.println("MemberMoneyCreatedEvent Sourcing Handler");
        id = UUID.randomUUID().toString();
        membershipId = Long.parseLong(event.getMembershipId());
        balance = 0;
    }

    @EventSourcingHandler
    public void on(IncreaseMemberMoneyEvent event) {//IncreaseMoneyEvent를 소싱하는 핸듣러
        System.out.println("IncreaseMemberMoneyEvent Sourcing Handler");
        id = event.getAggregateIdentifier();
        membershipId = Long.parseLong(event.getTargetMembershipId());
        balance = event.getAmount();
    }

    public MemberMoneyAggregate() {
    }
}
