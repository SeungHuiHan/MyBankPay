package com.bankpay.money.moneyservice.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.Date;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE) //접근 제어자를 private하게 가져간다
public class MoneyChangingRequest {

    private final String moneyChangingRequestId;

    //어떤 고객의 증액/감액 요청을 요청했는지의 멤버 정보
    private final String targetMembershipId;

    // 그 요청이 증액 요청인지 / 감액 요청인지
    private final int moneyChangingType; // 0: 증액, 1:감액 - 나중에 enum

//    enum ChangingType {
//        INCREASING,//증액
//        DECREASING //감액
//    }

    // 증액 또는 감액 요청의 금액
    private final int changingMoneyAmount;

    //머니 번역 요청에 대한 상태
    private final int changingMoneyStatus; // 0:요청, 1:성공, 2:실패

//    enum ChangingMoneyStatus{
//        REQUESTED, //요청됨
//        SUCCEEDED, //성공
//        FAILED, //상태
//        CANCELLED //취소
//    }

    private final String uuid;
    private final Date createdAt;



    //이 클래스를 통하지 않고는 FirmbankingRequest이라는 객체를 만들 수 없다
    public static MoneyChangingRequest generateMoneyChangingRequest(MoneyChangingRequestId moneyChangingRequestId,
                                                                    TargetMembershipId targetMembershipId,
                                                                    MoneyChangingType moneyChangingType,
                                                                    ChangingMoneyAmount changingMoneyAmount,
                                                                    ChangingMoneyStatus changingMoneyStatus,
                                                                    Uuid uuid
                                                                    //CreatedAt createdAt


          ) {
        return new MoneyChangingRequest(
                moneyChangingRequestId.getMoneyChangingRequestId(),
                targetMembershipId.getTargetMembershipId(),
                moneyChangingType.getMoneyChangingType(),
                changingMoneyAmount.getChangingMoneyAmount(),
                changingMoneyStatus.getChangingMoneyStatus(),
                uuid.getUuid(),
                new Date()
              );
    }

    //싱글톤
    @Value
    public static class MoneyChangingRequestId{
        public MoneyChangingRequestId(String value){
            this.moneyChangingRequestId=value;
        }
       String moneyChangingRequestId;
    }

    @Value
    public static class TargetMembershipId{
        public TargetMembershipId(String value){
            this.targetMembershipId=value;
        }
        String targetMembershipId;
    }

    @Value
    public static class MoneyChangingType{
        public MoneyChangingType(int value){
            this.moneyChangingType=value;
        }
        int moneyChangingType;
    }

    @Value
    public static class ChangingMoneyAmount{
        public ChangingMoneyAmount(int value){
            this.changingMoneyAmount=value;
        }
        int changingMoneyAmount;
    }

    @Value
    public static class ChangingMoneyStatus{
        public ChangingMoneyStatus(int value){
            this.changingMoneyStatus=value;
        }
        int changingMoneyStatus;
    }

    @Value
    public static class Uuid{
        public Uuid(UUID value){
            this.uuid=value.toString();
        }
        String uuid;
    }

//    @Value
//    public static class CreatedAt{
//        public CreatedAt(Date value){
//            this.createdAt=value;
//        }
//        Date createdAt;
//    }




}
