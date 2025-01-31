package com.bankpay.money.moneyservice.application.port.in;

import com.bankpay.money.moneyservice.adapter.out.persistence.MemberMoneyJpaEntity;
import com.bankpay.money.moneyservice.domain.MemberMoney;

public interface GetMemberMoneyPort {
    MemberMoneyJpaEntity getMemberMoney(
            MemberMoney.MembershipId memberId
    );
}
