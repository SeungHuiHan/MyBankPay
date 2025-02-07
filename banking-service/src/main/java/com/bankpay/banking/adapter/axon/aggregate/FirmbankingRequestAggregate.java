package com.bankpay.banking.adapter.axon.aggregate;

import com.bankpay.banking.adapter.axon.command.CreateFirmbankingRequestCommand;
import com.bankpay.banking.adapter.axon.command.UpdateFirmbankingRequestCommand;
import com.bankpay.banking.adapter.axon.event.RequestFirmbankingCreatedEvent;
import com.bankpay.banking.adapter.axon.event.UpdateRequestFirmbankingEvent;
import com.bankpay.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.bankpay.banking.adapter.out.external.bank.FirmbankingResult;
import com.bankpay.banking.application.port.out.RequestExternalFirmbankingPort;
import com.bankpay.banking.application.port.out.RequestFirmbankingPort;
import com.bankpay.banking.domain.FirmbankingRequest;
import com.bankpay.common.command.RequestFirmbankingCommand;
import com.bankpay.common.command.RollbackFirmbankingRequestCommand;
import com.bankpay.common.event.RequestFirmbankingFinishedEvent;
import com.bankpay.common.event.RollbackFirmbankingFinishedEvent;
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
public class FirmbankingRequestAggregate {

    @AggregateIdentifier
    private String id;

    private String fromBankName;
    private String fromBankAccountNumber;
    private String toBankName;
    private String toBankAccountNumber;
    private int moneyAmount;
    private int firmbankingStatus;

    @CommandHandler
    public FirmbankingRequestAggregate (CreateFirmbankingRequestCommand command) {
        System.out.println("CreateFirmbankingRequestCommand Handler");
        apply(new RequestFirmbankingCreatedEvent(command.getFromBankName(), command.getFromBankAccountNumber(), command.getToBankName(), command.getToBankAccountNumber(), command.getMoneyAmount()));
    }

    @CommandHandler
    public FirmbankingRequestAggregate(RequestFirmbankingCommand command, RequestFirmbankingPort firmbankingPort, RequestExternalFirmbankingPort externalFirmbankingPort){
        System.out.println("FirmbankingRequestAggregate Handler");
        id = command.getAggregateIdentifier();

        // from (고객계좌)-> to(법인 계좌)
        // 펌뱅킹 수행!
        firmbankingPort.createFirmbankingRequest(
                new FirmbankingRequest.FromBankName(command.getToBankName()),
                new FirmbankingRequest.FromBankAccountNumber(command.getToBankAccountNumber()),
                new FirmbankingRequest.ToBankName("fastcampus-bank"),
                new FirmbankingRequest.ToBankAccountNumber("123-333-9999"),
                new FirmbankingRequest.MoneyAmount(command.getMoneyAmount()),
                new FirmbankingRequest.FirmbankingStatus(0),
                new FirmbankingRequest.FirmbankingAggregateIdentifier(id));

        // firmbanking!
        FirmbankingResult firmbankingResult = externalFirmbankingPort.requestExternalFirmbanking(
                new ExternalFirmbankingRequest(
                        command.getFromBankName(),
                        command.getFromBankAccountNumber(),
                        command.getToBankName(),
                        command.getToBankAccountNumber(),
                        command.getMoneyAmount()
                ));

        int resultCode = firmbankingResult.getResultCode();

        // 0. 성공, 1. 실패
        apply(new RequestFirmbankingFinishedEvent(
                command.getRequestFirmbankingId(),
                command.getRechargeRequestId(),
                command.getMembershipId(),
                command.getToBankName(),
                command.getToBankAccountNumber(),
                command.getMoneyAmount(),
                resultCode,
                id
        ));
    }

    @CommandHandler
    public FirmbankingRequestAggregate(@NotNull RollbackFirmbankingRequestCommand command, RequestFirmbankingPort firmbankingPort, RequestExternalFirmbankingPort externalFirmbankingPort) {
        System.out.println("RollbackFirmbankingRequestCommand Handler");
        id = UUID.randomUUID().toString();

        // rollback 수행 (-> 법인 계좌 -> 고객 계좌 펌뱅킹)
        firmbankingPort.createFirmbankingRequest(
                new FirmbankingRequest.FromBankName("fastcampus"),
                new FirmbankingRequest.FromBankAccountNumber("123-333-9999"),
                new FirmbankingRequest.ToBankName(command.getBankName()),
                new FirmbankingRequest.ToBankAccountNumber(command.getBankAccountNumber()),
                new FirmbankingRequest.MoneyAmount(command.getMoneyAmount()),
                new FirmbankingRequest.FirmbankingStatus(0),
                new FirmbankingRequest.FirmbankingAggregateIdentifier(id));

        // firmbanking!
        FirmbankingResult result = externalFirmbankingPort.requestExternalFirmbanking(
                new ExternalFirmbankingRequest(
                        "fastcampus",
                        "123-333-9999",
                        command.getBankName(),
                        command.getBankAccountNumber(),
                        command.getMoneyAmount()
                ));

        int res = result.getResultCode(); //정상이라고 가정

        apply(new RollbackFirmbankingFinishedEvent(
                command.getRollbackFirmbankingId(),
                command.getMembershipId(),
                id)
        );
    }

    @CommandHandler
    public String handle(UpdateFirmbankingRequestCommand command) {
        System.out.println("UpdateFirmbankingRequestCommand Handler");

        id = command.getAggregateIdentifier();
        apply(new UpdateRequestFirmbankingEvent(command.getFirmbankingStatus()));

        return id;
    }

    @EventSourcingHandler
    public void on (RequestFirmbankingCreatedEvent event) {
        System.out.println("RequestFirmbankingCreatedEvent Sourcing Handler");
        id = UUID.randomUUID().toString();
        fromBankName = event.getFromBankName();
        fromBankAccountNumber = event.getFromBankAccountNumber();
        toBankName = event.getToBankName();
        toBankAccountNumber = event.getToBankAccountNumber();
    }

    @EventSourcingHandler
    public void on (UpdateRequestFirmbankingEvent event) {
        System.out.println("UpdateRequestFirmbankingEvent Sourcing Handler");
        firmbankingStatus = event.getFirmbankingStatus();
    }

    public FirmbankingRequestAggregate() {
    }
}