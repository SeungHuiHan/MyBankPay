package com.bankpay.money.moneyservice.application.port.in;

import com.bankpay.money.moneyservice.domain.MemberMoney;
import com.bankpay.money.moneyservice.domain.MoneyChangingRequest;

import java.util.List;


public interface IncreaseMoneyRequestUseCase {
    MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command);

    //비동기
    MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command);

    void increaseMoneyRequestByEvent(IncreaseMoneyRequestCommand command);

    List<MemberMoney> findMemberMoneyListByMembershipIds(FindMemberMoneyListByMembershipIdsCommand command);
}
