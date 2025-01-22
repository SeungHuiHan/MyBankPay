package com.bankpay.money.moneyservice.adapter.in.web;

import com.bankpay.money.moneyservice.adapter.out.persistence.MoneyChangingRequestJpaEntity;
import com.bankpay.money.moneyservice.domain.MoneyChangingRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MoneyChangingResultDetailMapper {
    public MoneyChangingResultDetail mapToMoneyChangingResultDetail(MoneyChangingRequest moneyChangingRequest){
//        return  new MoneyChangingResultDetail(
//                moneyChangingRequest.getMoneyChangingRequestId(),
//                moneyChangingRequest.getChangingType(),
//                moneyChangingRequest.getChangingMoneyStatus(),
//                moneyChangingRequest.getChangingAmount()
//        );
        return null;
    }

//    public static MoneyChangingResultDetail mapFromMoneyChangingResult(MoneyChangingRequest moneyChangingResult){
//        return new MoneyChangingResultDetail(
//                moneyChangingResult.getMoneyChangingRequestId(),
//                moneyChangingResult.getChangingType(),
//                moneyChangingResult.getChangingMoneyStatus(),
//                moneyChangingResult.getChangingAmount()
//        );
//    }
}
