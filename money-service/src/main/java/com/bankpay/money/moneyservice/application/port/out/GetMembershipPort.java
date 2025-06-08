package com.bankpay.money.moneyservice.application.port.out;


public interface GetMembershipPort {
    public MembershipStatus getMembership(String membershipId);
}
