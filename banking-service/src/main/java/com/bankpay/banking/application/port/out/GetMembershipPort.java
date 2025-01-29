package com.bankpay.banking.application.port.out;


import com.bankpay.banking.adapter.out.service.Membership;

public interface GetMembershipPort {
    public MembershipStatus getMembership(String membershipId);
}
