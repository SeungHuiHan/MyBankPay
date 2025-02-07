package com.bankpay.money.moneyservice.application.port.out;

import com.bankpay.money.moneyservice.adapter.out.persistence.MemberMoneyJpaEntity;

import java.util.List;

public interface GetMemberMoneyListPort {
    List<MemberMoneyJpaEntity> getMemberMoneyPort(List<String> membershipIds);
}
