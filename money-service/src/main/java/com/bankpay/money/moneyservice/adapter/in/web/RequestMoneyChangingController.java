package com.bankpay.money.moneyservice.adapter.in.web;

import com.bankpay.common.WebAdapter;
import com.bankpay.money.moneyservice.application.port.in.*;
import com.bankpay.money.moneyservice.domain.MemberMoney;
import com.bankpay.money.moneyservice.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class  RequestMoneyChangingController {

    private final IncreaseMoneyRequestUseCase increaseMoneyRequestUseCase;
    //private final DecreaseMoneyRequestUseCase decreaseMoneyRequestUseCase;
    private final CreateMemberMoneyUseCase createMemberMoneyUseCase;

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


    @PostMapping("/money/decrease-eda")
    public MoneyChangingResultDetail decreaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request) {
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount()*-1) //사실상 감액
                .build();

        increaseMoneyRequestUseCase.increaseMoneyRequestByEvent(command);
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


    @PostMapping(path = "/money/create-member-money")
    void createMemberMoney (@RequestBody CreateMemberMoneyRequest request) {
        createMemberMoneyUseCase.createMemberMoney(
                CreateMemberMoneyCommand.builder().membershipId(request.getMembershipId()).build());
    }

    @PostMapping(path = "/money/increase-eda")
    void increaseMoneyChangingRequestByEvent(@RequestBody IncreaseMoneyChangingRequest request) {
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();

        increaseMoneyRequestUseCase.increaseMoneyRequestByEvent(command);
    }

    @PostMapping(path = "/money/member-money")
    List<MemberMoney> findMemberMoneyListByMembershipIds(@RequestBody FindMemberMoneyListByMembershipIdsRequest request) {
        FindMemberMoneyListByMembershipIdsCommand command = FindMemberMoneyListByMembershipIdsCommand.builder()
                .membershipIds(request.getMembershipIds())
                .build();

        return increaseMoneyRequestUseCase.findMemberMoneyListByMembershipIds(command);
    }
}
