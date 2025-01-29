package com.bankpay.money.moneyservice.application.port.in;

import com.bankpay.money.moneyservice.domain.MoneyChangingRequest;


public interface IncreaseMoneyRequestUseCase {
    MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command);

    //비동기
    MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command);
}
