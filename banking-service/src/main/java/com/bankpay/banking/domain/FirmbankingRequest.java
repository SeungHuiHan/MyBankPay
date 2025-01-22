package com.bankpay.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE) //접근 제어자를 private하게 가져간다
public class FirmbankingRequest {

    private final String firmbankingRequestId;

    private final String fromBankName;

    private final String fromBankAccountNumber;

    private final String toBankName;

    private final String toBankAccountNumber;

    private final int moneyAmount; //only won

    private final int firmbankingStatus; //0:요청, 1: 완료. 2:실패

    private final UUID uuid; //


    //이 클래스를 통하지 않고는 FirmbankingRequest이라는 객체를 만들 수 없다
    public static FirmbankingRequest generateFirmbankingRequest(FirmbankingRequestId firmbankingRequestId,
                                                                FromBankName fromBankName,
                                                                FromBankAccountNumber fromBankAccountNumber,
                                                                ToBankName toBankName,
                                                                ToBankAccountNumber toBankAccountNumber,
                                                                MoneyAmount moneyAmount,
                                                                FirmbankingStatus firmbankingStatus,
                                                                UUID uuid

          ) {
        return new FirmbankingRequest(
                firmbankingRequestId.firmbankingRequestId,
                fromBankName.fromBankName,
                fromBankAccountNumber.fromBankAccountNumber,
                toBankName.toBankName,
                toBankAccountNumber.toBankAccountNumber,
                moneyAmount.moneyAmount,
                firmbankingStatus.firmbankingStatus,
                uuid
              );
    }

    //싱글톤
    @Value
    public static class FirmbankingRequestId{
        public FirmbankingRequestId(String value){
            this.firmbankingRequestId=value;
        }
       String firmbankingRequestId;
    }

    @Value
    public static class FromBankName{
        public FromBankName(String value){
            this.fromBankName=value;
        }
        String fromBankName;
    }

    @Value
    public static class FromBankAccountNumber{
        public FromBankAccountNumber(String value){
            this.fromBankAccountNumber=value;
        }
        String fromBankAccountNumber;
    }

    @Value
    public static class ToBankName{
        public ToBankName(String value){
            this.toBankName=value;
        }
        String toBankName;
    }

    @Value
    public static class ToBankAccountNumber{
        public ToBankAccountNumber(String value){
            this.toBankAccountNumber=value;
        }
        String toBankAccountNumber;
    }

    @Value
    public static class MoneyAmount{
        public MoneyAmount(int value){
            this.moneyAmount=value;
        }
        int moneyAmount;
    }

    @Value
    public static class FirmbankingStatus{
        public FirmbankingStatus(int value){
            this.firmbankingStatus=value;
        }
        int firmbankingStatus;
    }



}
