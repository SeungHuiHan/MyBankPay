package com.bankpay.banking.adapter.in.web;

import com.bankpay.banking.application.port.in.RequestFirmbankingCommand;
import com.bankpay.banking.application.port.in.RequestFirmbankingUseCase;
import com.bankpay.banking.application.port.in.UpdateFirmbankingCommand;
import com.bankpay.banking.application.port.in.UpdateFirmbankingUseCase;
import com.bankpay.banking.domain.FirmbankingRequest;
import com.bankpay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmbankingController {

    private final RequestFirmbankingUseCase requestFirmbankingUseCase;
    private final UpdateFirmbankingUseCase updateFirmbankingUseCase;

    @PostMapping("/banking/firmbanking/request")
    public FirmbankingRequest requestFirmbanking(@RequestBody RequestFirmbankingRequest request) {

        //request ~~

        //request -> Command (추상화 계층)

        //useCase ~~ (request)

        RequestFirmbankingCommand command = RequestFirmbankingCommand.builder()
                .fromBankName(request.getFromBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .toBankName(request.getToBankName())
                .toBankAccountNumber(request.getToBankAccountNumber())
                .moneyAmount(request.getMoneyAmount())
                .build();

        return requestFirmbankingUseCase.requestFirmbanking(command);

    }

    @PostMapping(path = "/banking/firmbanking/request-eda")
    void requestFirmbankingByEvent(@RequestBody RequestFirmbankingRequest request) {
        RequestFirmbankingCommand command = RequestFirmbankingCommand.builder()
                .toBankName(request.getToBankName())
                .toBankAccountNumber(request.getToBankAccountNumber())
                .fromBankName(request.getFromBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .moneyAmount(request.getMoneyAmount())
                .build();

        requestFirmbankingUseCase.requestFirmbankingByEvent(command);
    }

    @PutMapping(path = "/banking/firmbanking/update-eda")
    void updateFirmbankingByEvent(@RequestBody UpdateFirmbankingRequest request) {
        UpdateFirmbankingCommand command = UpdateFirmbankingCommand.builder()
                .firmbankingAggregateIdentifier(request.getFirmbankingRequestAggregateIdentifier())
                .firmbankingStatus(request.getStatus()).build();

        updateFirmbankingUseCase.updateFirmbankingByEvent(command);
    }
}
