package com.bankpay.payment.application.port.out;

public interface GetMembershipPort {
    public MembershipStatus getMembership(String membershipId);
}
