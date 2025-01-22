package com.bankpay.banking.adapter.in.web;

import com.bankpay.banking.application.port.in.FirmbankingRequestCommand;
import com.bankpay.banking.application.port.in.RegisterBankAccountCommand;
import com.bankpay.banking.application.port.in.RegisterBankAccountUseCase;
import com.bankpay.banking.application.port.in.RequestFirmbankingUseCase;
import com.bankpay.banking.domain.FirmbankingRequest;
import com.bankpay.banking.domain.RegisteredBankAccount;
import com.bankpay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmbankingController {

    private final RequestFirmbankingUseCase requestFirmbankingUseCase;

    @PostMapping("/banking/firmbankibg/request")
    public FirmbankingRequest requestFirmbanking(@RequestBody RequestFirmbankingRequest request) {

        //request ~~

        //request -> Command (추상화 계층)

        //useCase ~~ (request)

        FirmbankingRequestCommand command = FirmbankingRequestCommand.builder()
                .fromBankName(request.getFromBankName())
                .fromBankAccountNumber(request.getFromBankAccountNumber())
                .toBankName(request.getToBankName())
                .toBankAccountNumber(request.getToBankAccountNumber())
                .moneyAmount(request.getMoneyAmount())
                .build();

        return requestFirmbankingUseCase.requestFirmbanking(command);

    }
}
// private final String fromBankName;
//    @NotNull
//    private final String fromBankAccountNumber;
//    @NotNull
//    private final String toBankName;
//    @NotNull
//    private final String toBankAccountNumber;
//    @NotNull
//    private final int moneyAmount; //only won
//    @NotNull
//    private final int firmbankingStatus;