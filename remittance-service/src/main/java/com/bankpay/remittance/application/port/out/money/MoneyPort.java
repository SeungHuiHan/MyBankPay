package com.bankpay.remittance.application.port.out.money;

public interface MoneyPort {
    MoneyInfo getMoneyInfo(String membershipId);
    boolean requestMoneyRecharging(String membershipId, int amount); //membershipId에 대해서 amount만큼 충전해줘
    boolean requestMoneyIncrease(String membershipId, int amount); //membershipId에 대해서 amount만큼  증액
    boolean requestMoneyDecrease(String membershipId, int amount); //membershipId에 대해서 amount만큼  감액
}

