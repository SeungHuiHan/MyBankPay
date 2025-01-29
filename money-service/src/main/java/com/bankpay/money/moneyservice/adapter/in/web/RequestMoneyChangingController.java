package com.bankpay.money.moneyservice.adapter.in.web;

import com.bankpay.common.WebAdapter;
import com.bankpay.money.moneyservice.application.port.in.IncreaseMoneyRequestCommand;
import com.bankpay.money.moneyservice.application.port.in.IncreaseMoneyRequestUseCase;
import com.bankpay.money.moneyservice.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class  RequestMoneyChangingController {

    private final IncreaseMoneyRequestUseCase increaseMoneyRequestUseCase;
    //private final DecreaseMoneyRequestUseCase decreaseMoneyRequestUseCase;

    @PostMapping("/money/increase")
    public MoneyChangingResultDetail increaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request) {

        //request ~~

        //request -> Command (추상화 계층)

        //useCase ~~ (request)

        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();


        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequest(command);

        //MoneyChangingRequest->MoneyChangingResultDetail
        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                0,
                0,
                moneyChangingRequest.getChangingMoneyAmount()
        );
        return resultDetail;

    }


    @PostMapping("/money/decrease")
    public MoneyChangingResultDetail decreaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request) {

//        FirmbankingRequestCommand command = FirmbankingRequestCommand.builder()
//                .fromBankName(request.getFromBankName())
//                .fromBankAccountNumber(request.getFromBankAccountNumber())
//                .toBankName(request.getToBankName())
//                .toBankAccountNumber(request.getToBankAccountNumber())
//                .moneyAmount(request.getMoneyAmount())
//                .build();
//
//        return decreaseMoneyRequestUseCase.decreaseMoneyRequest(command);

        return null;
    }


    @PostMapping("/money/increase-async")
    public MoneyChangingResultDetail increaseMoneyChangingRequestAsync(@RequestBody IncreaseMoneyChangingRequest request) {

        //request ~~

        //request -> Command (추상화 계층)

        //useCase ~~ (request)

        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();


        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequestAsync(command);

        //MoneyChangingRequest->MoneyChangingResultDetail
        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                0,
                0,
                moneyChangingRequest.getChangingMoneyAmount()
        );
        return resultDetail;

    }
}
