package com.bankpay.money.moneyservice.adapter.out.persistence;

import com.bankpay.money.moneyservice.domain.MoneyChangingRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MoneyChangingRequestMapper {
    public MoneyChangingRequest mapToDomainEntity(MoneyChangingRequestJpaEntity moneyChangingRequestJpaEntity){
        return  MoneyChangingRequest.generateMoneyChangingRequest(
                new MoneyChangingRequest.MoneyChangingRequestId(moneyChangingRequestJpaEntity.getMoneyChangingRequestId()+""),
                new MoneyChangingRequest.TargetMembershipId(moneyChangingRequestJpaEntity.getTargetMembershipId()),
                new MoneyChangingRequest.MoneyChangingType(moneyChangingRequestJpaEntity.getMoneyChangingType()),
                new MoneyChangingRequest.ChangingMoneyAmount(moneyChangingRequestJpaEntity.getMoneyAmount()),
                new MoneyChangingRequest.ChangingMoneyStatus(moneyChangingRequestJpaEntity.getChangingMoneyStatus()),
                new MoneyChangingRequest.Uuid(moneyChangingRequestJpaEntity.getUuid())

        );
    }
}
///  Integer.parseInt(changingAmount.changingAmount),
//                changingMoneyStatus.changingMoneyStatus,
//                uuid.uuid,
//                new Date()

// //어떤 고객의 증액/감액 요청을 요청했는지의 멤버 정보
//    private String targetMembershipId;
//
//    // 그 요청이 증액 요청인지 / 감액 요청인지
//    private int moneyChangingType; // 0: 증액, 1:감액 - 나중에 enum
//
//    // 증액 또는 감액 요청의 금액
//    private int moneyAmount;
//
//    //머니 번역 요청에 대한 상태
//    private int ChangingMoneyStatus;
//
//    private String uuid;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    private Timestamp createdAt;