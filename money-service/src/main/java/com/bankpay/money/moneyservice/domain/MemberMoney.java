package com.bankpay.money.moneyservice.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.Date;
import java.util.UUID;

//지갑
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE) //접근 제어자를 private하게 가져간다
public class MemberMoney {

    private final String memberMoneyId;

    private final String membershipId;

    // 잔액
    private final int balance;


    //private final int linkedBankAccount;





    //이 클래스를 통하지 않고는 FirmbankingRequest이라는 객체를 만들 수 없다
    public static MemberMoney generateMemberMoney(MemberMoneyId memberMoneyId,
                                                  MembershipId membershipId,
                                                  Balance balance


          ) {
        return new MemberMoney(
                memberMoneyId.getMemberMoneyId(),
                membershipId.getMembershipId(),
                balance.getBalance()

              );
    }

    //싱글톤
    @Value
    public static class MemberMoneyId{
        public MemberMoneyId(String value){
            this.memberMoneyId=value;
        }
       String memberMoneyId;
    }

    @Value
    public static class MembershipId{
        public MembershipId(String value){
            this.membershipId=value;
        }
        String membershipId;
    }

    @Value
    public static class Balance{
        public Balance(int value){
            this.balance=value;
        }
        int balance;
    }






}
