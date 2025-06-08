package com.bankpay.banking.domain;

import com.bankpay.banking.adapter.axon.aggregate.RegisteredBankAccountAggregate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE) //접근 제어자를 private하게 가져간다

public class RegisteredBankAccount {

    private final String registeredBankAccountId;

    private final String membershipId;

    private final String bankName;

    private final String bankAccountNumber;

    private final boolean linkedStatusIsValid;

    private final String aggregateIdentifier;


    //이 클래스를 통하지 않고는 Membership이라는 객체를 만들 수 없다
    public static RegisteredBankAccount generateRegisteredBankAccount(RegisteredBankAccountId registeredBankAccountId,
    RegisteredBankAccount.MembershipId membershipId,
    RegisteredBankAccount.BankName bankName,
    RegisteredBankAccount.BankAccountNumber bankAccountNumber,
    RegisteredBankAccount.LinkedStatusIsValid linkedStatusIsValid,
    RegisteredBankAccount.AggregateIdentifier aggregateIdentifier

          ) {
        return new RegisteredBankAccount(
                registeredBankAccountId.registeredBankAccountId,
                membershipId.membershipId,
                bankName.bankName,
                bankAccountNumber.bankAccountNumber,
                linkedStatusIsValid.linkedStatusIsValid,
                aggregateIdentifier.getAggregateIdentifier()
              );
    }

    //싱글톤
    @Value
    public static class RegisteredBankAccountId{
        public RegisteredBankAccountId(String value){
            this.registeredBankAccountId=value;
        }
       String registeredBankAccountId;
    }

    @Value
    public static class MembershipId{
        public MembershipId(String value){
            this.membershipId=value;
        }
        String membershipId;
    }

    @Value
    public static class BankName{
        public BankName(String value){
            this.bankName=value;
        }
        String bankName;
    }

    @Value
    public static class BankAccountNumber{
        public BankAccountNumber(String value){
            this.bankAccountNumber=value;
        }
        String bankAccountNumber;
    }

    @Value
    public static class LinkedStatusIsValid{
        public LinkedStatusIsValid(boolean value){
            this.linkedStatusIsValid=value;
        }
        boolean linkedStatusIsValid;
    }


    @Value
    public static class AggregateIdentifier {
        public AggregateIdentifier(String value) {
            this.aggregateIdentifier = value;
        }
        String aggregateIdentifier;
    }

}
