package com.bankpay.money.moneyservice.application.port.in;

import com.bankpay.money.moneyservice.domain.MoneyChangingRequest;


public interface DecreaseMoneyRequestUseCase {
    MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command);
}
