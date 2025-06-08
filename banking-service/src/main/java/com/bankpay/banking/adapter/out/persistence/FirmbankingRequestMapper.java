package com.bankpay.banking.adapter.out.persistence;

import com.bankpay.banking.domain.FirmbankingRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FirmbankingRequestMapper {
    public FirmbankingRequest mapToDomainEntity(FirmbankingRequestJpaEntity firmbankingRequestJpaEntity, UUID uuid){
        return  FirmbankingRequest.generateFirmbankingRequest(
                new FirmbankingRequest.FirmbankingRequestId(firmbankingRequestJpaEntity.getFirmbankingRequestId()+""),
                new FirmbankingRequest.FromBankName(firmbankingRequestJpaEntity.getFromBankName()),
                new FirmbankingRequest.FromBankAccountNumber(firmbankingRequestJpaEntity.getFromBankAccountNumber()),
                new FirmbankingRequest.ToBankName(firmbankingRequestJpaEntity.getToBankName()),
                new FirmbankingRequest.ToBankAccountNumber(firmbankingRequestJpaEntity.getToBankAccountNumber()),
                new FirmbankingRequest.MoneyAmount(firmbankingRequestJpaEntity.getMoneyAmount()),
                new FirmbankingRequest.FirmbankingStatus(firmbankingRequestJpaEntity.getFirmbankingStatus()),
                uuid,
                new FirmbankingRequest.FirmbankingAggregateIdentifier(firmbankingRequestJpaEntity.getAggregateIdentifier())
        );
    }
}
//  private Long firmbankingRequestId;
//
//    private String fromBankName;
//
//    private String fromBankAccountNumber;
//
//    private String toBankName;
//
//    private String toBankAccountNumber;
//
//    private int moneyAmount; //only won
//
//    private int firmbankingStatus; //0:요청, 1: 완료. 2:실패