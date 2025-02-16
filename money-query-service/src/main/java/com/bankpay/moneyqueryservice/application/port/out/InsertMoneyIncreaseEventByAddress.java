package com.bankpay.moneyqueryservice.application.port.out;

public interface InsertMoneyIncreaseEventByAddress {
    void insertMoneyIncreaseEventByAddress(String addressName, int moneyIncrease);
}
