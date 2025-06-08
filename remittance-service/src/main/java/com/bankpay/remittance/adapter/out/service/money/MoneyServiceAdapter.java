package com.bankpay.remittance.adapter.out.service.money;

import com.bankpay.common.CommonHttpClient;
import com.bankpay.common.ExternalSystemAdapter;
import com.bankpay.remittance.application.port.out.money.MoneyInfo;
import com.bankpay.remittance.application.port.out.money.MoneyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class MoneyServiceAdapter implements MoneyPort {

    private CommonHttpClient moneyServiceHttpClient;

    @Value("${service.money.url}")
    private String moneyServiceEndpoint;

    public MoneyServiceAdapter(CommonHttpClient moneyServiceHttpClient, String moneyServiceEndpoint) {
        this.moneyServiceHttpClient = moneyServiceHttpClient;
        this.moneyServiceEndpoint = moneyServiceEndpoint;
    }

    @Override
    public MoneyInfo getMoneyInfo(String membershipId) {
        return null;
    }

    @Override
    public boolean requestMoneyRecharging(String membershipId, int amount) {
        return false;
    }

    @Override
    public boolean requestMoneyIncrease(String membershipId, int amount) {
        return false;
    }

    @Override
    public boolean requestMoneyDecrease(String membershipId, int amount) {
        return false;
    }
}