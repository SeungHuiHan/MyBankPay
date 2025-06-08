package com.bankpay.money.moneyservice.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="money_changing_request")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyChangingRequestJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moneyChangingRequestId;

    //어떤 고객의 증액/감액 요청을 요청했는지의 멤버 정보
    private String targetMembershipId;

    // 그 요청이 증액 요청인지 / 감액 요청인지
    private int moneyChangingType; // 0: 증액, 1:감액 - 나중에 enum

    // 증액 또는 감액 요청의 금액
    private int moneyAmount;

    //머니 번역 요청에 대한 상태
    private int changingMoneyStatus; //0:요청, 1:성공, 2:실패

    private UUID uuid;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Override
    public String toString() {
        return "MoneyChangingRequestJpaEntity{" +
                "moneyChangingRequestId=" + moneyChangingRequestId +
                ", targetMembershipId='" + targetMembershipId + '\'' +
                ", moneyChangingType=" + moneyChangingType +
                ", moneyAmount=" + moneyAmount +
                ", changingMoneyStatus=" + changingMoneyStatus +
                ", uuid='" + uuid + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public MoneyChangingRequestJpaEntity(String targetMembershipId, int moneyChangingType, int moneyAmount, int changingMoneyStatus, UUID  uuid, Timestamp createdAt) {
        this.targetMembershipId = targetMembershipId;
        this.moneyChangingType = moneyChangingType;
        this.moneyAmount = moneyAmount;
        this.changingMoneyStatus= changingMoneyStatus;
        this.uuid = uuid;
        this.createdAt = createdAt;
    }
}

