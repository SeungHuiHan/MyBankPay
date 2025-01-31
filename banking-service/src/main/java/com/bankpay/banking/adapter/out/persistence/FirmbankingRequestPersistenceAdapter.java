package com.bankpay.banking.adapter.out.persistence;

import com.bankpay.banking.adapter.out.external.bank.FirmbankingResult;
import com.bankpay.banking.application.port.out.RequestExternalFirmbankingPort;
import com.bankpay.banking.application.port.out.RequestFirmbankingPort;
import com.bankpay.banking.domain.FirmbankingRequest;
import com.bankpay.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@PersistenceAdapter
@RequiredArgsConstructor
public class FirmbankingRequestPersistenceAdapter implements RequestFirmbankingPort {

    private final SpringDataFirmbankingRequestRepository requestFirmbankingRepository;

    @Override
    public FirmbankingRequestJpaEntity createFirmbankingRequest(FirmbankingRequest.FromBankName fromBankName, FirmbankingRequest.FromBankAccountNumber fromBankAccountNumber, FirmbankingRequest.ToBankName toBankName, FirmbankingRequest.ToBankAccountNumber toBankAccountNumber,
                                                                FirmbankingRequest.MoneyAmount moneyAmount, FirmbankingRequest.FirmbankingStatus firmbankingStatus, FirmbankingRequest.FirmbankingAggregateIdentifier firmbankingAggregateIdentifier) {
        return requestFirmbankingRepository.save(new FirmbankingRequestJpaEntity(
                fromBankName.getFromBankName(),
                fromBankAccountNumber.getFromBankAccountNumber(),
                toBankName.getToBankName(),
                toBankAccountNumber.getToBankAccountNumber(),
                moneyAmount.getMoneyAmount(),
                firmbankingStatus.getFirmbankingStatus(),
                UUID.randomUUID(),
                firmbankingAggregateIdentifier.getFirmbankingAggregateIdentifier()
                )

        );
    }

    @Override
    public FirmbankingRequestJpaEntity modifyFirmbankingRequest(FirmbankingRequestJpaEntity entity) {
        return requestFirmbankingRepository.save(entity);
    }

    @Override
    public FirmbankingRequestJpaEntity getFirmbankingRequest(FirmbankingRequest.FirmbankingAggregateIdentifier firmbankingAggregateIdentifier) {
        List<FirmbankingRequestJpaEntity> entityList=requestFirmbankingRepository.findByAggregateIdentifier(firmbankingAggregateIdentifier.getFirmbankingAggregateIdentifier());
        if(entityList.size()>=1) {
            return entityList.get(0);
        }

        return null;
    }
}
