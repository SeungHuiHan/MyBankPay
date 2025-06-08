package com.bankpay.money.moneyservice.application.port.out;

import com.bankpay.money.moneyservice.adapter.out.persistence.MemberMoneyJpaEntity;
import com.bankpay.money.moneyservice.adapter.out.persistence.MoneyChangingRequestJpaEntity;
import com.bankpay.money.moneyservice.domain.MemberMoney;
import com.bankpay.money.moneyservice.domain.MoneyChangingRequest;

public interface IncreaseMoneyPort {

     MoneyChangingRequestJpaEntity createMoneyChangingMoney(
            MoneyChangingRequest.TargetMembershipId targetMembershipId,
            MoneyChangingRequest.MoneyChangingType changingType,
            MoneyChangingRequest.ChangingMoneyAmount changingAmount,
            MoneyChangingRequest.ChangingMoneyStatus changingMoneyStatus,
            MoneyChangingRequest.Uuid uuid
    );

     MemberMoneyJpaEntity increaseMoney(
             MemberMoney.MembershipId memberId,

             int increaseAmount
     );


}

