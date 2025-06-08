package com.bankpay.money.moneyservice.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//이 request가 줄 데이터가 무엇인지
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncreaseMoneyChangingRequest {


    private String targetMembershipId;

    //무조건 증액 요청 (충전)
    private int amount; //only won

}

// private final String moneyChangingRequestId;
//
//    //어떤 고객의 증액/감액 요청을 요청했는지의 멤버 정보
//    private final String targetMembershipId;
//
//    // 그 요청이 증액 요청인지 / 감액 요청인지
//    private final ChangingType changingType; // 0: 증액, 1:감액 - 나중에 enum
//
//    enum ChangingType {
//        INCREASING,//증액
//        DECREASING //감액
//    }
//
//    // 증액 또는 감액 요청의 금액
//    private final int changingAmount;
//
//    //머니 번역 요청에 대한 상태
//    private final ChangingMoneyStatus ChangingMoneyStatus;
//
//    enum ChangingMoneyStatus{
//        REQUESTED, //요청됨
//        SUCCEEDED, //성공
//        FAILED, //상태
//        CANCELLED //취소
//    }
//
//    private final String uuid;
//    private final Date createdAt;