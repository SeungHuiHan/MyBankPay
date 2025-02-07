package com.bankpay.money.moneyservice.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="member_money")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberMoneyJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberMoneyId;

    private Long membershipId;

    // 잔액
    private int balance;

    private String aggregateIdentifier;


    public MemberMoneyJpaEntity(Long membershipId, int balance,String aggregateIdentifier) {
        this.membershipId = membershipId;
        this.balance = balance;
        this.aggregateIdentifier = aggregateIdentifier;
    }

    @Override
    public String toString() {
        return "MemberMoneyJpaEntity{" +
                "memberMoneyId=" + memberMoneyId +
                ", membershipId='" + membershipId + '\'' +
                ", balance=" + balance +
                ", aggregateIdentifier='" + aggregateIdentifier + '\'' +
                '}';
    }
}

